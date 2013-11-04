package engine.debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.usikkert.kouinject.annotation.Component;

import engine.core.Container;
import engine.core.ITime;

@Component
@Singleton
public class Profiler implements IProfiler {
	
	class ProfilePoint {
		public final String name;
		public int calls;
		public long startTime;
		public long prevTime;
		public long cumulTime;
		
		public ProfilePoint(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof ProfilePoint))
				return false;
			ProfilePoint other = (ProfilePoint) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		
		public String toString() {
			return name + "|" + calls + "|" + prevTime + "|" + cumulTime/calls + "|" + cumulTime + "\n";
		}
		
	}
	
	@Inject
	private ITime time;
	
	private HashMap<String, ProfilePoint> points;
	
	
	public Profiler() {
		points = new HashMap<String, Profiler.ProfilePoint>();
	}
	
	/* (non-Javadoc)
	 * @see engine.debug.IProfiler#start(java.lang.String)
	 */
	@Override
	public void start(String point) {
		ProfilePoint ppoint;
		if(!points.containsKey(point))
			points.put(point, (ppoint = new ProfilePoint(point)));
		else
			ppoint = points.get(point);
		
		ppoint.startTime = time.getTime(); 
		ppoint.calls++;
	}
	
	/* (non-Javadoc)
	 * @see engine.debug.IProfiler#end(java.lang.String)
	 */
	@Override
	public void end(String point) {
		if(!points.containsKey(point))
			throw new IllegalStateException();
		
		ProfilePoint pp = points.get(point);
		pp.prevTime = time.getTime() - pp.startTime;
		pp.cumulTime += pp.prevTime;
	}

	@Override
	public String toString() {
		ArrayList<ProfilePoint> list = new ArrayList<ProfilePoint>(points.values());
		Collections.sort(list, new Comparator<ProfilePoint>() {
			public int compare(ProfilePoint o1, ProfilePoint o2) {
				return o2.name.compareTo(o1.name);
			}
		});
		
		StringBuilder sb = new StringBuilder();
		
		for(ProfilePoint p : list)
			sb.append(p.toString());
		
		return sb.toString();
	}

}
