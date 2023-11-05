package be.condorcet.api3haninisprojet2_1;

import be.condorcet.api3haninisprojet2_1.entities.Taxi;
import be.condorcet.api3haninisprojet2_1.services.taxi.TaxiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/taxis")
public class GestTaxi {

    //PAS FINI

    @Autowired
    TaxiServiceImpl taxiService;

    @RequestMapping("/tous")
    public String affTous(Map<String, Object> model) {
        List<Taxi> liste;
        try {
            liste = taxiService.all();
            model.put("mesTaxis", liste);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de taxis : " + e);
            return "error";
        }
        return "affichageTousTaxis";
    }

    @RequestMapping("/selection")
    String selection(@RequestParam("id") int id, Map<String, Object> model) {
        Taxi taxi = null;
        Optional<Taxi> otaxi;

        try {
            taxi = taxiService.read(id);
            model.put("monTaxi", taxi);
            model.put("mesLocations", taxi.getLlocations());
            model.put("mesClientsPourTaxi", taxi.getClientsByTaxi());
            model.put("mesKmParcourus", taxi.getKilometresParcourus());
            model.put("monMontantTotal", taxi.getMontantTotalDesLocations());

        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du taxi : " + e);
            return "error";
        }
        return "affichageTaxi";
    }

    @RequestMapping("/selectionCarburant")
    String selection(@RequestParam("carburant") String carburant, Map<String, Object> model) {
        List<Taxi> taxis = null;

        try {
            taxis = taxiService.read(carburant);
            model.put("mesTaxis", taxis);
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture : " + e);
            return "error";
        }
        return "affichageTaxisCarburant";
    }
}
