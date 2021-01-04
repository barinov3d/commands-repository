package org.barino3d.models.impl;


import org.barino3d.models.Application;

public interface ApplicationCustomizeRepository<T, ID> {

    Application findByName(String name);

}
