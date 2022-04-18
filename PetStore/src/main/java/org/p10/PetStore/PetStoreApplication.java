package org.p10.PetStore;

import org.p10.PetStore.Controllers.PetController;
import org.p10.PetStore.Controllers.StoreController;
import org.p10.PetStore.Controllers.UserController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class PetStoreApplication extends Application {

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();
        set.add(new PetController());
        set.add(new StoreController());
        set.add(new UserController());
        return set;
    }
}