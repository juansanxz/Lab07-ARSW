package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.impl.Tuple;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
//@Primary
public class SubsamplingBlueprintFilter implements BlueprintFilter {
    @Override
    public Blueprint getFilteredBlueprint(Blueprint bp) {
        int initialSize = bp.getPoints().size();
        if (initialSize % 2 != 0 && initialSize != 1) {
            initialSize++;
        }
        Point[] newPts = new Point[bp.getPoints().size() - (initialSize/2)];
        for (int i = 0; i < initialSize/2 ; i++) {
            newPts[i] = bp.getPoints().get(i*2);
        }
        Blueprint filteredBp = new Blueprint(bp.getAuthor(), bp.getName(),newPts);
        return filteredBp;
    }
}
