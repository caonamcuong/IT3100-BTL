package enemy.test02;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BasicWall;
import bIO.BoundingBox;
import bIO.Vec2f;
import enemy.EnemyHurtBox;
import player.Player;
import player.PlayerHitbox;

public class EnemyArrow extends EnemyHurtBox {
    private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
        put("idle", new BasicSprite(
                "arrow.png",
                Arrays.asList(0),
                Arrays.asList(0),
                Arrays.asList(137),
                Arrays.asList(16)
        ));
        put("destroying", null);
        put("destroyed", null);
    }};
    @Override
    public BasicSprite getSprite() { return state_machine.get(getState()); }

    private Vec2f velocity;
    private BasicTimer live_timer;

    public EnemyArrow(BasicIO io) {
        super(io);
        setState("idle");
        setPosition(new Vec2f(0,0));
        setBBox(new BoundingBox(16, 4));
        setBBoxOrigin(new Vec2f(0.5f, 0.5f));
        setSpriteOrigin(new Vec2f(0f, 0.5f));
        setBBoxDrawFlag(getIO().getDebug());
        setScale(0.5f);

        velocity = new Vec2f(0,0);
        live_timer = new BasicTimer(io.getStepPerSec() * 5, new Runnable() {
            public void run() {
                setState("destroying");
                live_timer.stop();
            }
        });
        live_timer.setup();
    }
    public EnemyArrow(BasicIO io, Vec2f position, Vec2f velo) {
        this(io);
        setPosition(position);
        setVelocity(velo);
    }

    public void setVelocity(Vec2f f) {
        velocity = f;
        setHFlip(f.getX().toFloat() > 0);
        setSpriteOrigin(new Vec2f(getHFlip()?1.0f:0.0f, 0.5f));
        
    }
    public void setVelocity(float a, float b) {
        velocity = new Vec2f(a,b);
    }

    @Override
    public void fixedUpdate() {
        if (getState() == "destroying") {
            setState("destroyed");
            getIO().removeObject(this);
            return;
        }
        else if (getState() == "destroyed") {
            return;
        }
        live_timer.run();

        Vec2f mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));
        Vec2f old_pos = getPosition();
        setPosition(getPosition().add(mov_step));

        List<BasicObject> o = getIO().quadQueryObject(new BoundingBox(
                new BasicNumber(128), new BasicNumber(128),
                getBBox().getX().sub(new BasicNumber(64)),
                getBBox().getY().sub(new BasicNumber(64))
        ));
        o.removeIf(p -> !((p instanceof BasicWall) || (p instanceof PlayerHitbox) || (p instanceof Player)));

        for (BasicObject w: o) {
            if (w.getBBox().collideWith(getBBox())) {
                setState("destroying");
                return;
            }
        }
    }

    @Override
    public void postUpdate() {
        bboxUpdate();
        if (getPosition().getX().lt(new BasicNumber(-10))) {
            getIO().removeObject(this);
        }
        else if (getPosition().getX().gt(new BasicNumber(650))) {
            getIO().removeObject(this);
        }
    }
}
