package enemy.test04;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import player.Player;
import bIO.*;
import enemy.EnemyHurtBox;
import enemy.EnemyPlayerTest;
import enemy.EnemyWallTest;
import player.Player;
import player.PlayerHitbox;
import enemy.test02.EnemyArrow;
import static java.lang.Math.abs;

public class Archers extends EnemyHurtBox {
    private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
        put("idle", new BasicSprite (
                "Skele_War_bow.png",
                Arrays.asList(7),
                Arrays.asList(32),
                Arrays.asList(19),
                Arrays.asList(32)
        ));
        put("detecting", new BasicSprite (
                "Skele_War_bow.png",
                Arrays.asList(2, 36),
                Arrays.asList(0, 0),
                Arrays.asList(26, 20),
                Arrays.asList(32, 32)
        ));
        put("attack", new BasicSprite (
                "Skele_War_bow.png",
                Arrays.asList(34, 7, 66),
                Arrays.asList(32, 32, 0),
                Arrays.asList(30, 19, 25),
                Arrays.asList(32, 32, 32)
        ));
        put ("destroying", null);
        put ("destroyed", null);
    }};
    @Override
    public BasicSprite getSprite() { return state_machine.get(getState()); }

    private static final float detect_xrange = 400;
    private static final float detect_yrange = 120;
    private static final float grav_speed = 0.2f;
    private static final float mov_speed = 300f;
    private static final long detect_time = BasicIO.getStepPerSec() * 2;
    private static final long attack_time = BasicIO.getStepPerSec() * 4;
    private static final long shoot_time = BasicIO.getStepPerSec();
    Player player;


    private Vec2f velocity;
    private int direction;
    private BasicTimer detect_timer;
    private boolean detect_flag;
    private BasicController control;
    private BasicTimer shoot_timer;
    private BasicTimer attack_timer;


    public Archers(BasicIO io) {
        super(io);
        setState("idle");
        setPosition(new Vec2f(0,0));
        setBBox(new BoundingBox(20, 30));
        setBBoxOrigin(new Vec2f(0.5f, 1.0f));
        setBBoxDrawFlag(getIO().getDebug());
        setSpriteOrigin(new Vec2f(0.5f, 1.0f));
        setScale(1.5f);
        setSpritePlayback(1);
        control = null;


        velocity = new Vec2f(0,0);
        direction = 0;
        detect_timer = new BasicTimer(detect_time, new Runnable() {
            public void run() {
                detect_flag = true;
            }
        });
        detect_flag = false;

        attack_timer = new BasicTimer(attack_time, new Runnable() {
            public void run() {
                setState("idle");
            }
        });
        shoot_timer = new BasicTimer(shoot_time, new Runnable() {
        	public void run() {
        		if (control != null) {
        			getIO().playSound("smb_fireball.wav");
                	long dir = direction == 0 ? -1 : 1;
                	control.addObject(new EnemyArrow(getIO(), getPosition().sub(new Vec2f(4*direction, 25)), new Vec2f(-1200 * dir, 0)));
                }
        	}
        });
        attack_timer.setup();

    }

    public Archers(BasicIO io, BasicController control) {
        this(io);
        this.control = control;
    }

    public Archers(BasicIO io, float x, float y) {
        this(io);
        setPosition(new Vec2f(x,y));
    }
    public Archers(BasicIO io, BasicController controller, float x, float y) {
    	this(io, controller);
    	setPosition(new Vec2f(x, y));
    }

    @Override
    public void fixedUpdate() {
        if (getState() == "destroying") {
            setState("destroyed");
            getIO().playSound("smb_kick_02.wav");
            getIO().removeObject(this);
            return;
        }
        else if (getState() == "destroyed") {
            return;
        }

        if (getPosition().getX().toFloat() < -64f) {
            setState("destroying");
            return;
        }

        Vec2f mov_step = new Vec2f(0,0);
        velocity.setX(new BasicNumber(0));
        velocity = velocity.add(new BasicNumber(0), new BasicNumber(grav_speed));

        List<BasicObject> o = getIO().quadQueryObject(new BoundingBox(
                new BasicNumber(detect_xrange*2), new BasicNumber(detect_yrange*2),
                getBBox().getX().sub(new BasicNumber(detect_xrange)),
                getBBox().getY().sub(new BasicNumber(detect_yrange))
        ));
        List<BasicObject> wallList = o.stream().filter(i -> i instanceof BasicWall).toList();
        List<BasicObject> playerList = o.stream().filter(i -> i instanceof Player).toList();
        List<BasicObject> playerHb = o.stream().filter(i -> i instanceof PlayerHitbox).toList();

        for (BasicObject pHb : playerHb) {
            if (pHb.getBBox().collideWith(getBBox())) {
                setState("destroying");
                return;
            }
        }

        if (getState() == "idle") {
            if (playerList.size() >= 1) {
                detect_timer.setup();
                setState("detecting");
            }
        }
        else if (getState() == "detecting") {
            if (playerList.size() == 0) {
                setState("idle");
            }
            else {
                BasicObject p = playerList.get(0);
                if (p.getPosition().sub(getPosition()).getX().lt(0)) direction = 1;
                else direction = 0;

                detect_timer.run();

                if (detect_flag) {
                    detect_flag = false;
                    
                    attack_timer.setup();
                    setState("attack");
                }
            }
        } 
        else if (getState() == "attack") {
        	if (!shoot_timer.getRunning()) shoot_timer.setup();
        	shoot_timer.run();
            attack_timer.run();
        }

        mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));

        BasicNumber dx = mov_step.getX();
        for (int i = 0; i < 32; ++i) {
            boolean do_collide = false;
            BasicObject collision_o = null;
            for (BasicObject w: wallList) {
                if (getBBox().add(dx, new BasicNumber(0)).collideWith(w.getBBox())) {
                    do_collide=true;
                    collision_o = w;
                    break;
                }
            }
            if (do_collide) {
                dx = dx.div(2f);
                if (i == 31) {
                    if (dx.gt(new BasicNumber(0f))) dx = collision_o.getBBox().getX().sub(getBBox().getX2());
                    else dx = collision_o.getBBox().getX2().sub(getBBox().getX());
                }
            }
            else break;
        }
        if (abs(dx.toFloat()) < 0.0001f) dx = new BasicNumber(0);
        mov_step.setX(dx);

        BasicNumber dy = mov_step.getY();
        for (int i = 0; i < 32; ++i) {
            boolean do_collide = false;
            BasicObject collision_o = null;
            for (BasicObject w: wallList) {
                if (getBBox().add(new BasicNumber(0), dy).collideWith(w.getBBox())) {
                    do_collide=true;
                    collision_o = w;
                    break;
                }
            }
            if (do_collide) {
                velocity.setY(new BasicNumber(0));
                dy = dy.div(2f);
                if (i == 31) {
                    if (dy.toFloat() > 0f) dy = collision_o.getBBox().getY().sub(getBBox().getY2());
                    else dy = collision_o.getBBox().getY2().sub(getBBox().getY());
                }
                dy = dy.div(2f);

                //hacky-ish
                if (i == 31) {
                    if (dy.toFloat() > 0f) dy = collision_o.getBBox().getY().sub(getBBox().getY2());
                    else dy = collision_o.getBBox().getY2().sub(getBBox().getY());
                }
            }
            else break;
        }
        if (abs(dy.toFloat()) < 0.0001f) dy = new BasicNumber(0);
        mov_step.setY(dy);

        Vec2f old_pos = getPosition();
        setPosition(getPosition().add(mov_step));
        getIO().quadUpdateObject(this, old_pos);
    }

    @Override
    public void postUpdate() {
        setHFlip(direction==0);
        bboxUpdate();
    }
}