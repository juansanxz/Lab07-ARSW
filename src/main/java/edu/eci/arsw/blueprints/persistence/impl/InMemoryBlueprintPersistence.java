/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final ConcurrentHashMap<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(140, 140)};
        Blueprint bp=new Blueprint("Santiago", "Planos 170",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        Point[] pts2=new Point[]{new Point(120, 40),new Point(11, 115)};
        Blueprint bp2=new Blueprint("Santiago", "Centro Comercial Santafe",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);

        Point[] pts3=new Point[]{new Point(20, 240),new Point(101, 511)};
        Blueprint bp3=new Blueprint("Juan", "Centro Comercial Colina",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);

        Point[] pts4=new Point[]{new Point(168, 410),new Point(119, 58)};
        Blueprint bp4=new Blueprint("Julieta", "Centro Comercial Galerias",pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.putIfAbsent(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getAuthorBlueprints(String author) {
        Set<Blueprint> blueprintSet = new HashSet<>();
        for (Tuple tuple :blueprints.keySet()){
            if(author.equals(tuple.getElem1())) {
                blueprintSet.add(blueprints.get(tuple));
            }
        }
        return blueprintSet;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> blueprintSet = new HashSet<>();
        for (Blueprint blueprint :blueprints.values()){
            blueprintSet.add(blueprint);
        }

        return blueprintSet;
    }

    @Override
    public void putBlueprintFiltered(Blueprint bpf){
        blueprints.put(new Tuple<>(bpf.getAuthor(), bpf.getName()), bpf);
    }

    @Override
    public void updateBlueprint(String author, String bpname, List<Point> points) throws BlueprintNotFoundException {
        if (!blueprints.containsKey(new Tuple<>(author,bpname))){
            throw new BlueprintNotFoundException("The given blueprint " + bpname + " from " + author + " doesn't exist.");
        }
        else{
            blueprints.get(new Tuple<>(author,bpname)).setPoints(points);
        }
    }
    
}
