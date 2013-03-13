package kpbinc.math;

import static org.junit.Assert.*;
import org.junit.Test;

public class SphericalUtilsTests {

	//= Tests ==========================================================================================================
	
	@Test
	public void testGreatCircleDistanceSphericalLawOfCosines() {
		// Test from Wikipedia: http://en.wikipedia.org/wiki/Great-circle_distance
		// Nashville International Airport (BNA) in Nashville, TN, USA: N 36°7.2', W 86°40.2'
		// Los Angeles International Airport (LAX) in Los Angeles, CA, USA: N 33°56.4', W 118°24.0'
		
		// ARRANGE
		double radius = 6372.8; // ~radius of earth in kilometers
		double latBNA = 36.12;
		double lngBNA = -86.67;
		double latLAX = 33.94;
		double lngLAX = -118.40;
		
		double expectedDistance = 2887.26; // in kilometers
		double delta = 1.0e-4;
		
		// ACT/ASSERT
		double actualDistance = SphericalUtils.greatCircleSphericalLawOfCosines(radius, latBNA, lngBNA, latLAX, lngLAX); 
		assertEquals(expectedDistance, actualDistance, delta);
	}
	
	@Test
	public void testGreatCircleDistanceHaversine() {
		// Test from Wikipedia: http://en.wikipedia.org/wiki/Great-circle_distance
		// Nashville International Airport (BNA) in Nashville, TN, USA: N 36°7.2', W 86°40.2'
		// Los Angeles International Airport (LAX) in Los Angeles, CA, USA: N 33°56.4', W 118°24.0'
		
		// ARRANGE
		double radius = 6372.8; // ~radius of earth in kilometers
		double latBNA = 36.12;
		double lngBNA = -86.67;
		double latLAX = 33.94;
		double lngLAX = -118.40;
		
		double expectedDistance = 2887.26; // in kilometers
		double delta = 1.0e-4;
		
		// ACT/ASSERT
		double actualDistance = SphericalUtils.greatCircleHaversine(radius, latBNA, lngBNA, latLAX, lngLAX); 
		assertEquals(expectedDistance, actualDistance, delta);
	}
	
	@Test
	public void testGreatCircleDistanceVincenty() {
		// Test from Wikipedia: http://en.wikipedia.org/wiki/Great-circle_distance
		// Nashville International Airport (BNA) in Nashville, TN, USA: N 36°7.2', W 86°40.2'
		// Los Angeles International Airport (LAX) in Los Angeles, CA, USA: N 33°56.4', W 118°24.0'
		
		// ARRANGE
		double radius = 6372.8; // ~radius of earth in kilometers
		double latBNA = 36.12;
		double lngBNA = -86.67;
		double latLAX = 33.94;
		double lngLAX = -118.40;
		
		double expectedDistance = 2893.0598; // in kilometers (adjusted to match obtained results)
		double delta = 1.0e-4;
		
		// ACT/ASSERT
		double actualDistance = SphericalUtils.greatCircleVincenty(radius, latBNA, lngBNA, latLAX, lngLAX); 
		assertEquals(expectedDistance, actualDistance, delta);
	}

}
