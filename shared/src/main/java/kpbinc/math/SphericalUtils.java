package kpbinc.math;

import static java.lang.Math.*;

public class SphericalUtils {

	public static final double EARTH_RADIUS_km = 6371; // approximately as per http://en.wikipedia.org/wiki/Earth_radius
	public static final double EARTH_RADIUS_mi = 3959; // approximately as per http://en.wikipedia.org/wiki/Earth_radius
	
	/**
	 * @param radius radius with which to calculate the great circle distance
	 * @param latitudeStandpoint latitude in degrees of the standpoint
	 * @param longitudeStandpoint longitude in degrees of the standpoint
	 * @param latitudeForepoint latitude in degrees of the forepoint
	 * @param longitudeForepoint longitude in degrees of the forepoint
	 * @return great circle distance between the standpoint and forepoint relative to the given radius
	 */
	public static Double greatCircleSphericalLawOfCosines(
			double radius,
			double latitudeStandpoint,
			double longitudeStandpoint,
			double latitudeForepoint,
			double longitudeForepoint) {
		// see http://introcs.cs.princeton.edu/java/12types/
		// and http://introcs.cs.princeton.edu/java/12types/GreatCircle.java.html
		// and http://en.wikipedia.org/wiki/Great-circle_distance
		
		double latS = toRadians(latitudeStandpoint);
		double lngS = toRadians(longitudeStandpoint);
		double latF = toRadians(latitudeForepoint);
		double lngF = toRadians(longitudeForepoint);
		
		double arcAngle = acos(  (sin(latS) * sin(latF))
							   + (cos(latS) * cos(latF) * cos(lngF - lngS)));
		
		double distance = radius * arcAngle;
		
		return distance;
	}

	/**
	 * @param radius radius with which to calculate the great circle distance
	 * @param latitudeStandpoint latitude in degrees of the standpoint
	 * @param longitudeStandpoint longitude in degrees of the standpoint
	 * @param latitudeForepoint latitude in degrees of the forepoint
	 * @param longitudeForepoint longitude in degrees of the forepoint
	 * @return great circle distance between the standpoint and forepoint relative to the given radius
	 */
	public static Double greatCircleHaversine(
			double radius,
			double latitudeStandpoint,
			double longitudeStandpoint,
			double latitudeForepoint,
			double longitudeForepoint) {
		// see http://introcs.cs.princeton.edu/java/12types/
		// and http://introcs.cs.princeton.edu/java/12types/GreatCircle.java.html
		// and http://en.wikipedia.org/wiki/Great-circle_distance
		
		double latS = toRadians(latitudeStandpoint);
		double lngS = toRadians(longitudeStandpoint);
		double latF = toRadians(latitudeForepoint);
		double lngF = toRadians(longitudeForepoint);
		
		double latAbsDiff = abs(latF - latS);
		double lngAbsDiff = abs(lngF - lngS);
		double arcAngle = 2.0 * asin(sqrt(  pow(sin(0.5 * latAbsDiff), 2.0)
				                          + (cos(latS) * cos(latF) * pow(sin(0.5 * lngAbsDiff), 2.0))));
		
		double distance = radius * arcAngle;
		
		return distance;
	}
	
	/**
	 * @param radius radius with which to calculate the great circle distance
	 * @param latitudeStandpoint latitude in degrees of the standpoint
	 * @param longitudeStandpoint longitude in degrees of the standpoint
	 * @param latitudeForepoint latitude in degrees of the forepoint
	 * @param longitudeForepoint longitude in degrees of the forepoint
	 * @return great circle distance between the standpoint and forepoint relative to the given radius
	 */
	public static Double greatCircleVincenty(
			double radius,
			double latitudeStandpoint,
			double longitudeStandpoint,
			double latitudeForepoint,
			double longitudeForepoint) {
		// see http://introcs.cs.princeton.edu/java/12types/
		// and http://introcs.cs.princeton.edu/java/12types/GreatCircle.java.html
		// and http://en.wikipedia.org/wiki/Great-circle_distance
		
		double latS = toRadians(latitudeStandpoint);
		double lngS = toRadians(longitudeStandpoint);
		double latF = toRadians(latitudeForepoint);
		double lngF = toRadians(longitudeForepoint);
		
		double lngAbsDiff = abs(lngF - lngS);
		
		double topLeft = pow(cos(latF) * sin(lngAbsDiff), 2.0);
		double topRight = pow(  (cos(latS) * sin(latF))
							  - (sin(latS) * cos(latS) * cos(lngAbsDiff)), 2.0);
		double top = sqrt(topLeft + topRight);
		double bottom = (  (sin(latS) * sin(latF))
				         + (cos(latS) * cos(latF) * cos(lngAbsDiff)));
		
		double arcAngle = atan2(top, bottom);
		
		double distance = radius * arcAngle;
		
		return distance;
	}
	
}
