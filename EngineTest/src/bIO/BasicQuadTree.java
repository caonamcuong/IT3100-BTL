package bIO;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BasicQuadTree {
	private class Node{
		BoundingBox bbox;
		int cur_object;
		List<BasicObject> list_object;
		
		Node p;
		Node nw, ne, sw, se;
	}
	private int max_object;
	
	private Node nil;
	private Node end;
	
	private Node newNode(BoundingBox bbox, Node p) {
		Node n = new Node();
		n.bbox = bbox;
		n.p = p;
		n.nw = n.ne = n.sw = n.se = nil;
		n.cur_object = 0;
		n.list_object = Collections.synchronizedList(new LinkedList<BasicObject>());
		return n;
	}
	private Node getRoot() {
		return end.nw;
	}
	
	public BasicQuadTree(BoundingBox bbox, int max) {
		nil = new Node();
		end = new Node();
		nil.p = nil.nw = nil.ne = nil.sw = nil.se = nil;
		end.p = end.nw = end.ne = end.sw = end.se = nil;
		max_object = max;
		
		end.nw = newNode(bbox, end);
	}
	
	private void splitNode(Node n) {
		if (n.cur_object <= max_object) return;
		BoundingBox cur_bb = n.bbox;
		
		n.nw = newNode(new BoundingBox(
				cur_bb.getX(), cur_bb.getY(),
				cur_bb.getWidth().div(2f), 
				cur_bb.getHeight().div(2f)
			), n);
		n.ne = newNode(new BoundingBox(
				cur_bb.getX().add(cur_bb.getWidth().div(2f)), cur_bb.getY(),
				cur_bb.getWidth().div(2f), cur_bb.getHeight().div(2f)
			), n);
		n.sw = newNode(new BoundingBox(
				cur_bb.getX(), cur_bb.getY().add(cur_bb.getHeight().div(2f)),
				cur_bb.getWidth().div(2f), cur_bb.getHeight().div(2f)
			), n);
		n.se = newNode(new BoundingBox(
				cur_bb.getX().add(cur_bb.getWidth().div(2f)), 
				cur_bb.getY().add(cur_bb.getHeight().div(2f)),
				cur_bb.getWidth().div(2f), 
				cur_bb.getHeight().div(2f)
			), n);
		
		for (BasicObject o: n.list_object) {
			if (n.nw.bbox.containPoint(o.getBBox().getPosition())) {
				n.nw.list_object.add(o);
				n.nw.cur_object += 1;
			}
			else if (n.ne.bbox.containPoint(o.getBBox().getPosition())) {
				n.ne.list_object.add(o);
				n.ne.cur_object += 1;
			}
			else if (n.sw.bbox.containPoint(o.getBBox().getPosition())) {
				n.sw.list_object.add(o);
				n.sw.cur_object += 1;
			}
			else {
				n.se.list_object.add(o);
				n.se.cur_object += 1;
			}
		}
		
		n.cur_object = -1;
		n.list_object.clear();
		
		splitNode(n.nw); splitNode(n.ne);
		splitNode(n.sw); splitNode(n.se);
	}
	
	public void addObject(BasicObject o) {
		Node n = getRoot();
		while (n.cur_object == -1) {
			if (n.nw.bbox.containPoint(o.getBBox().getPosition())) n = n.nw;
			else if (n.ne.bbox.containPoint(o.getBBox().getPosition())) n = n.ne;
			else if (n.sw.bbox.containPoint(o.getBBox().getPosition())) n = n.sw;
			else n = n.se;
		}
		n.list_object.add(o);
		n.cur_object += 1;
		splitNode(n);
	}
	public void removeObject(BasicObject o) {
		Node n = getRoot();
		while (n.cur_object == -1) {
			if (n.nw.bbox.containPoint(o.getBBox().getPosition())) n = n.nw;
			else if (n.ne.bbox.containPoint(o.getBBox().getPosition())) n = n.ne;
			else if (n.sw.bbox.containPoint(o.getBBox().getPosition())) n = n.sw;
			else n = n.se;
		}
		n.list_object.remove(o);
		n.cur_object -= 1;
	}
	public void updateObject(BasicObject o, Vec2f old_pos) {
		Node n = getRoot();
		while (n.cur_object == -1) {
			if (n.nw.bbox.containPoint(old_pos)) n = n.nw;
			else if (n.ne.bbox.containPoint(old_pos)) n = n.ne;
			else if (n.sw.bbox.containPoint(old_pos)) n = n.sw;
			else n = n.se;
		}
		if (!n.bbox.containPoint(o.getBBox().getPosition())) {
			n.list_object.remove(o);
			n.cur_object -= 1;
			addObject(o);
		}
	}
	private void queryObjectAll(List<BasicObject> l, Node n) {
		if (n.cur_object != -1) {
			for (BasicObject o: n.list_object) l.add(o);
			System.out.println("hello");
		}
		else {
			queryObjectAll(l, n.nw);
			queryObjectAll(l, n.ne);
			queryObjectAll(l, n.sw);
			queryObjectAll(l, n.se);
		}
	}
	private void queryObject(List<BasicObject> l, Node n, BoundingBox bbox) {
		if (n.bbox.insideOf(bbox)) queryObjectAll(l, n);
		else {
			if (n.cur_object != -1) {
				for (BasicObject o: n.list_object) {
					if (bbox.containPoint(o.getBBox().getPosition())) {
						l.add(o);
					}
				}
				return;
			}
			if (n.nw.bbox.collideWith(bbox)) queryObject(l, n.nw, bbox);
			if (n.ne.bbox.collideWith(bbox)) queryObject(l, n.ne, bbox);
			if (n.sw.bbox.collideWith(bbox)) queryObject(l, n.sw, bbox);
			if (n.se.bbox.collideWith(bbox)) queryObject(l, n.se, bbox);
		}
	}
	public List<BasicObject> queryObject(BoundingBox bbox) {
		List<BasicObject> ret_list = new LinkedList<BasicObject>();
		queryObject(ret_list, getRoot(), bbox);
		return ret_list;
	}
	
}
