package kpbinc.spring.security;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityTests {

	@Test
	public void testEqualsSelf() {
		// ARRANGE
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		
		// ACT/ASSERT
		assertEquals(authority, authority);
	}
	
	@Test
	public void testEqualsIdentical() {
		// ARRANGE
		GrantedAuthority authority1 = new SimpleGrantedAuthority("ROLE_USER");
		GrantedAuthority authority2 = new SimpleGrantedAuthority("ROLE_USER");
		
		// ACT/ASSERT
		assertEquals(authority1, authority2);
		assertEquals(authority2, authority1);
	}

	@Test
	public void testNotEqualsNull() {
		// ARRANGE
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		
		// ACT/ASSERT
		assertNotEquals(authority, null);
	}
	
	@Test
	public void testNotEqualsOther() {
		// ARRANGE
		GrantedAuthority authority1 = new SimpleGrantedAuthority("ROLE_USER");
		GrantedAuthority authority2 = new SimpleGrantedAuthority("ROLE_DRIVER");
		
		// ACT/ASSERT
		assertNotEquals(authority1, authority2);
		assertNotEquals(authority2, authority1);
	}
	
	@Test
	public void testNotEqualsNonType() {
		// ARRANGE
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		Object object = new Object();
		
		// ACT/ASSERT
		assertNotEquals(authority, object);
	}
}
