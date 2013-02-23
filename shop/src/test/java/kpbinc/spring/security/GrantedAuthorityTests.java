package kpbinc.spring.security;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import kpbinc.cs462.ffds.model.GrantedAuthorityRoles;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityTests {

	//------------------------------------------------------------------------------------------------------------------
	// Instance Equality Tests
	//------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void testEqualsSelf() {
		// ARRANGE
		GrantedAuthority authority = GrantedAuthorityRoles.ROLE_USER;
		
		// ACT/ASSERT
		assertEquals(authority, authority);
	}
	
	@Test
	public void testEqualsIdentical() {
		// ARRANGE
		GrantedAuthority authority1 = GrantedAuthorityRoles.ROLE_USER;
		GrantedAuthority authority2 = new SimpleGrantedAuthority(authority1.getAuthority());
		
		// ACT/ASSERT
		assertEquals(authority1, authority2);
		assertEquals(authority2, authority1);
	}

	@Test
	public void testNotEqualsNull() {
		// ARRANGE
		GrantedAuthority authority = GrantedAuthorityRoles.ROLE_USER;
		
		// ACT/ASSERT
		assertNotEquals(authority, null);
	}
	
	@Test
	public void testNotEqualsOther() {
		// ARRANGE
		GrantedAuthority authority1 = GrantedAuthorityRoles.ROLE_USER;
		GrantedAuthority authority2 = GrantedAuthorityRoles.ROLE_DRIVER;
		// ACT/ASSERT
		assertNotEquals(authority1, authority2);
		assertNotEquals(authority2, authority1);
	}
	
	@Test
	public void testNotEqualsNonType() {
		// ARRANGE
		GrantedAuthority authority = GrantedAuthorityRoles.ROLE_USER;
		Object object = new Object();
		
		// ACT/ASSERT
		assertNotEquals(authority, object);
	}
	
	//------------------------------------------------------------------------------------------------------------------
	// Collection Membership Tests
	//------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void testSelfMembershipInCollection() {
		// ARRANGE
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = GrantedAuthorityRoles.ROLE_USER;
		authorities.add(authority);
		
		// ACT/ASSERT
		assertTrue(authorities.contains(authority));
	}
	
	@Test
	public void testIdenticalMembershipInCollection() {
		// ARRANGE
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(GrantedAuthorityRoles.ROLE_USER);
		
		GrantedAuthority authorityCopy = new SimpleGrantedAuthority(GrantedAuthorityRoles.ROLE_USER.getAuthority());
		
		// ACT/ASSERT
		assertTrue(authorities.contains(authorityCopy));
	}
	
	@Test
	public void testSelfMembershipInWildcardCollection() {
		// ARRANGE
		GrantedAuthority authority = GrantedAuthorityRoles.ROLE_USER;
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(authority);
		
		// ACT/ASSERT
		assertTrue(authorities.contains(authority));
	}
	
	@Test
	public void testIdenticalMembershipInWildcardCollection() {
		// ARRANGE
		GrantedAuthority authority = GrantedAuthorityRoles.ROLE_USER;
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(authority);
		
		GrantedAuthority authorityCopy = new SimpleGrantedAuthority(GrantedAuthorityRoles.ROLE_USER.getAuthority());
		
		// ACT/ASSERT
		assertTrue(authorities.contains(authorityCopy));
	}
	
}