package consensusBN.Experiments;

import edu.cmu.tetrad.bayes.BayesIm;
import edu.cmu.tetrad.bayes.BayesPm;
import edu.cmu.tetrad.data.DiscreteVariable;
import edu.cmu.tetrad.graph.Dag;
import edu.cmu.tetrad.graph.Graph;
import edu.cmu.tetrad.graph.Node;
import consensusBN.Utils;
import weka.classifiers.bayes.net.BIFReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoldStandardSMHD {

    public static void main(String[] args) {
        List<String> networks = Arrays.asList(
                "earthquake", "asia", "sachs", "child", "water", "insurance", "alarm", "hailfinder",
                "hepar2", "mildew", "barley", "win95pts", "pathfinder",
                "andes", "pigs", "diabetes", "link", "munin"

        );

        String path = "./res/networks/"; // Ajusta esta ruta según tu estructura de directorios

        for (String network : networks) {
            try {
                // Cargar el gold-standard
                BIFReader reader = new BIFReader();
                reader.processFile(path + network + ".xbif");

                BIFReader bayesianReader = new BIFReader();
                String pathXBIF ="./res/networks/" + network + ".xbif";
                try {
                    bayesianReader.processFile(pathXBIF);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                BayesPm originalBN =  Utils.transformBayesNetToBayesPm(bayesianReader);
                Dag originalDag = new Dag(originalBN.getDag());

                // Crear red vacía con los mismos nodos
                Dag emptyDag = new Dag(originalDag.getNodes());

                // Calcular SHD
                int shd = Utils.SHD(originalDag, emptyDag);

                // Calcular SMHD
                double smhd = Utils.SMHD(originalDag, emptyDag);

                // Calcular fussim
                double fussim = Utils.fusionSimilarity(originalDag, emptyDag);

                System.out.println(network + "," + shd + "," + smhd + "," + fussim);



            } catch (Exception e) {
                System.err.println("Error procesando " + network + ": " + e.getMessage());
            }
        }
    }
}
