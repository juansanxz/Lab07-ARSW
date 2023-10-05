/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

import java.util.List;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public interface BlueprintsPersistence {
    
    /**
     * 
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;
    
    /**
     * 
     * @param author blueprint's author
     * @param bprintname blueprint's author
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String bprintname);


    /**
     *
     * @param author blueprint's author
     * @return a set of blueprints of the given author
     */
    public Set<Blueprint> getAuthorBlueprints(String author);

    /**
     *
     * @return all the blueprints
     */
    public Set<Blueprint> getAllBlueprints();

    public void putBlueprintFiltered(Blueprint BPF);

    /**
     *
     * @param bp the blueprint to update
     * @throws BlueprintNotFoundException if the blueprint doesn't exist,
     *    or any other low-level persistence error occurs.
     */
    public void updateBlueprint (String author, String bpname, List<Point> points) throws BlueprintNotFoundException;

}
