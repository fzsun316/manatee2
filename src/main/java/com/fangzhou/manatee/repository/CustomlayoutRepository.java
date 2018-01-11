package com.fangzhou.manatee.repository;

import com.fangzhou.manatee.domain.Customlayout;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Customlayout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomlayoutRepository extends JpaRepository<Customlayout,Long> {

    @Query("select customlayout from Customlayout customlayout where customlayout.layout_user.login = ?#{principal.username}")
    List<Customlayout> findByLayout_userIsCurrentUser();
    
}
