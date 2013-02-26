package kpbinc.spring.security;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityTests {

	//= Member Data ====================================================================================================
	
	private static final String PRIMARY_ROLE_NAME = "PRIMARY_ROLE";
	private static final GrantedAuthority PRIMARY_ROLE = new SimpleGrantedAuthority(PRIMARY_ROLE_NAME);
	
	private static final String SECONDARY_ROLE_NAME = "SECONDARY_ROLE";
	private static final GrantedAuthority SECONDARY_ROLE = new SimpleGrantedAuthority(SECONDARY_ROLE_NAME);
	
	
	//= Tests ==========================================================================================================
	
	//- Instance Equality Tests ----------------------------------------------------------------------------------------
	
	@Test
	public void testEqualsSelf() {
		// ARRANGE
		GrantedAuthority authority = PRIMARY_ROLE;
		
		// ACT/ASSERT
		assertEquals(authority, authority);
	}
	
	@Test
	public void testEqualsIdentical() {
		// ARRANGE
		GrantedAuthority authority1 = PRIMARY_ROLE;
		GrantedAuthority authority2 = new SimpleGrantedAuthority(authority1.getAuthority());
		
		// ACT/ASSERT
		assertEquals(authority1, authority2);
		assertEquals(authority2, authority1);
	}

	@Test
	public void testNotEqualsNull() {
		// ARRANGE
		GrantedAuthority authority = PRIMARY_ROLE;
		
		// ACT/ASSERT
		assertNotEquals(authority, null);
	}
	
	@Test
	public void testNotEqualsOther() {
		// ARRANGE
		GrantedAuthority authority1 = PRIMARY_ROLE;
		GrantedAuthority authority2 = SECONDARY_ROLE;
		// ACT/ASSERT
		assertNotEquals(authority1, authority2);
		assertNotEquals(authority2, authority1);
	}
	
	@Test
	public void testNotEqualsNonType() {
		// ARRANGE
		GrantedAuthority authority = PRIMARY_ROLE;
		Object object = new Object();
		
		// ACT/ASSERT
		assertNotEquals(authority, object);
	}
	
	//- Collection Membership Tests ------------------------------------------------------------------------------------
	
	@Test
	public void testSelfMembershipInCollection() {
		// ARRANGE
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = PRIMARY_ROLE;
		authorities.add(authority);
		
		// ACT/ASSERT
		assertTrue(authorities.contains(authority));
	}
	
	@Test
	public void testIdenticalMembershipInCollection() {
		// ARRANGE
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(PRIMARY_ROLE);
		
		GrantedAuthority authorityCopy = new SimpleGrantedAuthority(PRIMARY_ROLE.getAuthority());
		
		// ACT/ASSERT
		assertTrue(authorities.contains(authorityCopy));
	}
	
	@Test
	public void testSelfMembershipInWildcardCollection() {
		// ARRANGE
		GrantedAuthority authority = PRIMARY_ROLE;
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(authority);
		
		// ACT/ASSERT
		assertTrue(authorities.contains(authority));
	}
	
	@Test
	public void testIdenticalMembershipInWildcardCollection() {
		// ARRANGE
		GrantedAuthority authority = PRIMARY_ROLE;
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(authority);
		
		GrantedAuthority authorityCopy = new SimpleGrantedAuthority(PRIMARY_ROLE.getAuthority());
		
		// ACT/ASSERT
		assertTrue(authorities.contains(authorityCopy));
	}
	
}
