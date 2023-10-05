package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.impl.Tuple;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Primary
public class RedundanciesBlueprintFilter implements BlueprintFilter{

    @Override
    public Blueprint getFilteredBlueprint(Blueprint bp)   {

        Point currentPoint = null;
        Point nextPoint = null;
        int amountOfDuplicated = 0;
        List<Point> noDuplicatedList = new ArrayList<Point>();
        for (int i = 0; i < bp.getPoints().size() - 1; i++) {
            currentPoint = bp.getPoints().get(i);
            nextPoint = bp.getPoints().get(i+1);
            if (!currentPoint.equals(nextPoint) && amountOfDuplicated == 0) {
                noDuplicatedList.add(currentPoint);
            } else if (currentPoint.equals(nextPoint) && amountOfDuplicated == 0) {
                noDuplicatedList.add(currentPoint);
                amountOfDuplicated++;
            } else if (!currentPoint.equals(nextPoint)) {
                amountOfDuplicated = 0;
            }
            try {
                if ((i == bp.getPoints().size() - 2) && amountOfDuplicated == 0) {
                    noDuplicatedList.add(nextPoint);
                }
            } catch (Exception e) {
            }

        }

        Blueprint filteredBp = new Blueprint(bp.getAuthor(), bp.getName(),noDuplicatedList.toArray(new Point[0]));
        return filteredBp;
    }
}
