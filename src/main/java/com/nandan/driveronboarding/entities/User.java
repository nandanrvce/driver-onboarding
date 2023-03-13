package com.nandan.driveronboarding.entities;

import com.nandan.driveronboarding.enums.*;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "driver")
public class User implements UserDetails {
  @Id
  @GeneratedValue
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  @Embedded
  private Address address;
  private String phoneNumber;
  @Enumerated(EnumType.STRING)
  private TrackingDeviceStatus trackingDeviceStatus;
  @Enumerated(EnumType.STRING)
  private DriverRegistrationStatus driverRegistrationStatus;
  @Enumerated(EnumType.STRING)
  private DriverRideAvailabilityStatus driverRideAvailabilityStatus;
  @Enumerated(EnumType.STRING)
  private Role role;
  @OneToMany(mappedBy = "user")
  private List<Token> tokens;
  @OneToMany(mappedBy = "user")
  private List<FileInfo> files;
  @OneToMany(mappedBy = "user")
  private List<Vehicle> vehicles;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
