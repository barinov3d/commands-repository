package org.barino3d.models.impl;


import org.barino3d.models.Application;
import org.barino3d.models.UserDto;

import java.util.List;

public interface ApplicationCustomizeRepository<T, ID> {

    Application findByName(String name);

    List<Application> findAllByUser(UserDto user);

}
