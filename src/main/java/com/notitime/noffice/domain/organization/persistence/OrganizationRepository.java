package com.notitime.noffice.domain.organization.persistence;

import com.notitime.noffice.domain.organization.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
