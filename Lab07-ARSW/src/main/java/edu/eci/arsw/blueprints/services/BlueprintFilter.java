package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.impl.Tuple;

import java.util.Map;
import java.util.Set;

public interface BlueprintFilter {

    /**
     *
     * @return a set of the filtered blueprints.
     */
    public Blueprint getFilteredBlueprint(Blueprint bp) ;
}
