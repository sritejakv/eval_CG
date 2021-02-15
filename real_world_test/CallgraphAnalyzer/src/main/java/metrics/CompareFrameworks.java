package metrics;

import adapters.FrameworkAdapter;
import com.ibm.wala.util.collections.HashMapFactory;
import com.ibm.wala.util.collections.HashSetFactory;
import probe.CallEdge;
import probe.CallGraph;
import utils.Constants;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CompareFrameworks {

//    public Map<String, Map<String, FrameworkMetrics>> compareMetrics(List<FrameworkAdapter> frameworkAdapters) {
//        Map<String, Map<String, FrameworkMetrics>> frameworkMetricsMap = HashMapFactory.make();
//        for (String lib: Constants.realWorldLibraries) {
//            Map<String, FrameworkMetrics> frameworkMap = HashMapFactory.make();
//            for (FrameworkAdapter a : frameworkAdapters) {
//                frameworkMap.put(a.getCgAlgName(),
//                        new FrameworkMetrics(a.getFrameworkName(), a.getCgAlgName(), lib));
//            }
//
//            Metrics m = frameworkAdapters.get(0).getMetricsMap().get(lib).get(0);
//
//            for (CallEdge dynEdge: m.getTotalDynamicEdges()) {
//                boolean isPresent = true;
//                for (FrameworkAdapter adapter: frameworkAdapters) {
//                    Metrics curAdapterMetrics = adapter.getMetricsMap().get(lib).get(0);
//                    if (curAdapterMetrics.getTpEdges().contains(dynEdge)) {
//                      continue;
//                    } else {
//                        isPresent = false;
//                        break;
//                    }
//                }
//
//                if (isPresent) {
//                    for (FrameworkMetrics fMap: frameworkMap.values()) {
//                        fMap.addCommonEdge(dynEdge);
//                    }
//                }
//            }
//
//            frameworkMetricsMap.put(lib, frameworkMap);
//        }
//
//        for (String lib : Constants.realWorldLibraries){
//            Map<String, FrameworkMetrics> libMetrics = frameworkMetricsMap.get(lib);
//            FrameworkMetrics pyanMetrics = libMetrics.get(Constants.PYAN_CALL_GRAPH);
//            FrameworkMetrics c2fMetrics = libMetrics.get(Constants.CODE2FLOW_CALL_GRAPH);
//            FrameworkMetrics walaMetrics = libMetrics.get(Constants.WALA_NCFA_CALL_GRAPH);
//            String pyan_wala = Constants.PYAN_CALL_GRAPH + "_" + Constants.WALA_NCFA_CALL_GRAPH;
//            String wala_c2f = Constants.WALA_NCFA_CALL_GRAPH + "_" + Constants.CODE2FLOW_CALL_GRAPH;
//            String c2f_pyan = Constants.CODE2FLOW_CALL_GRAPH + "_" + Constants.PYAN_CALL_GRAPH;
//            String pyan_wala_c2f = Constants.PYAN_CALL_GRAPH + "_" + Constants.WALA_NCFA_CALL_GRAPH +
//                    "_" + Constants.CODE2FLOW_CALL_GRAPH;
//
//            if (pyanMetrics != null && walaMetrics != null) {
//                FrameworkMetrics pyanWalaMetrics = new FrameworkMetrics(pyan_wala, pyan_wala, lib);
//                pyanWalaMetrics.setDynEdges(pyanMetrics.getDynEdges());
//                Set<CallEdge> srcEdges = pyanMetrics.getStaticEdges();
//                Set<CallEdge> dstEdges = walaMetrics.getStaticEdges();
//                if (pyanMetrics.getStaticEdges().size() > walaMetrics.getStaticEdges().size()) {
//                    ExtractMetrics.compareEdgeSets(dstEdges, srcEdges, pyanWalaMetrics, true, false);
//                } else {
//                    ExtractMetrics.compareEdgeSets(srcEdges, dstEdges, pyanWalaMetrics, true, true);
//                }
//                frameworkMetricsMap.get(lib).put(pyan_wala, pyanWalaMetrics);
//
//                if (c2fMetrics != null) {
//                    // Edges present in all three frameworks
//                    FrameworkMetrics pyanWalaC2fMetrics = new FrameworkMetrics(pyan_wala_c2f, pyan_wala_c2f, lib);
//                    pyanWalaC2fMetrics.setDynEdges(pyanMetrics.getDynEdges());
//                    ExtractMetrics.compareEdgeSets(pyanWalaMetrics.getCommonEdges(), c2fMetrics.getStaticEdges(),
//                            pyanWalaC2fMetrics, true, false);
//                    pyanWalaC2fMetrics.setCommonEdges(pyanMetrics.getPresentDynEdges());
//                    frameworkMetricsMap.get(lib).put(pyan_wala_c2f, pyanWalaC2fMetrics);
//                }
//            }
//
//            if (pyanMetrics != null && c2fMetrics != null) {
//                FrameworkMetrics pyanC2fMetrics = new FrameworkMetrics(c2f_pyan, c2f_pyan, lib);
//                pyanC2fMetrics.setDynEdges(pyanMetrics.getDynEdges());
//                Set<CallEdge> srcEdges = pyanMetrics.getStaticEdges();
//                Set<CallEdge> dstEdges = c2fMetrics.getStaticEdges();
//                if (pyanMetrics.getStaticEdges().size() > c2fMetrics.getStaticEdges().size()) {
//                    ExtractMetrics.compareEdgeSets(srcEdges, dstEdges, pyanC2fMetrics, false, true);
//                } else {
//                    ExtractMetrics.compareEdgeSets(dstEdges, srcEdges, pyanC2fMetrics, false, true);
//                }
//                frameworkMetricsMap.get(lib).put(c2f_pyan, pyanC2fMetrics);
//            }
//
//            if (c2fMetrics != null && walaMetrics != null) {
//                FrameworkMetrics walaC2fMetrics = new FrameworkMetrics(wala_c2f, wala_c2f, lib);
//                walaC2fMetrics.setDynEdges(pyanMetrics.getDynEdges());
//                Set<CallEdge> srcEdges = walaMetrics.getStaticEdges();
//                Set<CallEdge> dstEdges = c2fMetrics.getStaticEdges();
//                if (pyanMetrics.getStaticEdges().size() > c2fMetrics.getStaticEdges().size()) {
//                    ExtractMetrics.compareEdgeSets(srcEdges, dstEdges, walaC2fMetrics, true, false);
//                } else {
//                    ExtractMetrics.compareEdgeSets(dstEdges, srcEdges, walaC2fMetrics, true, true);
//                }
//                frameworkMetricsMap.get(lib).put(wala_c2f, walaC2fMetrics);
//            }
//        }
//
//        return frameworkMetricsMap;
//    }

    public Map<String, Map<String, FrameworkMetrics>> compareFrameworks(List<FrameworkAdapter> frameworkAdapters)
            throws FileNotFoundException {
        Map<String, Map<String, FrameworkMetrics>> metricsMap = HashMapFactory.make();

        for (String lib : Constants.realWorldLibraries){

            Map<String, FrameworkMetrics> frameworkMap = HashMapFactory.make();
            for (FrameworkAdapter a : frameworkAdapters) {
                frameworkMap.put(a.getCgAlgName(),
                        new FrameworkMetrics(a.getFrameworkName(), a.getCgAlgName(), lib));
            }

            CallGraph dynamicCG = frameworkAdapters.get(0).getPyCallGraph().getDynamicCallGraph(lib).get(0);
            Set<CallEdge> dynEdges = dynamicCG.edges();

            for (CallEdge dynEdge: dynEdges) {
                boolean isPresent = true;

                for (FrameworkAdapter adapter : frameworkAdapters) {
                    if (frameworkMap.get(adapter.getCgAlgName()).getDynEdges() == null) {
                        frameworkMap.get(adapter.getCgAlgName()).setDynEdges(dynEdges);
                        if (adapter.getStaticCallGraph(lib) != null) {
                            frameworkMap.get(adapter.getCgAlgName()).setStaticEdges(
                                    adapter.getStaticCallGraph(lib).get(0).edges()
                            );
                        }
                    }

                    CallGraph staticCG = adapter.getStaticCallGraph(lib).get(0);
                    Set<CallEdge> staticEdges = staticCG.edges();
                    boolean isPresentInCurrentFramework = false;
                    for (CallEdge e: staticEdges) {
                        if (ExtractMetrics.compareEdges(dynEdge, e,
                                adapter.getFrameworkName().equals(Constants.WALA_FRAMEWORK))) {
                            isPresentInCurrentFramework = true;
                            frameworkMap.get(adapter.getCgAlgName()).addPresentDynEdge(dynEdge);
                            break;
                        }
                    }

                    if (!isPresentInCurrentFramework) {
                        isPresent = false;
                    }
                }

                if (isPresent) {
                    for (FrameworkMetrics m: frameworkMap.values()) {
                        m.addCommonEdge(dynEdge);
                    }
                }
            }
            metricsMap.put(lib, frameworkMap);
        }

        for (String lib : Constants.realWorldLibraries){
            Map<String, FrameworkMetrics> libMetrics = metricsMap.get(lib);
            FrameworkMetrics pyanMetrics = libMetrics.get(Constants.PYAN_CALL_GRAPH);
            FrameworkMetrics c2fMetrics = libMetrics.get(Constants.CODE2FLOW_CALL_GRAPH);
            FrameworkMetrics walaMetrics = libMetrics.get(Constants.WALA_NCFA_CALL_GRAPH);
            String pyan_wala = Constants.PYAN_CALL_GRAPH + "_" + Constants.WALA_NCFA_CALL_GRAPH;
            String wala_c2f = Constants.WALA_NCFA_CALL_GRAPH + "_" + Constants.CODE2FLOW_CALL_GRAPH;
            String c2f_pyan = Constants.CODE2FLOW_CALL_GRAPH + "_" + Constants.PYAN_CALL_GRAPH;
            String pyan_wala_c2f = Constants.PYAN_CALL_GRAPH + "_" + Constants.WALA_NCFA_CALL_GRAPH +
                    "_" + Constants.CODE2FLOW_CALL_GRAPH;

            if (pyanMetrics != null && walaMetrics != null) {
                FrameworkMetrics pyanWalaMetrics = new FrameworkMetrics(pyan_wala, pyan_wala, lib);
                pyanWalaMetrics.setDynEdges(pyanMetrics.getDynEdges());
                Set<CallEdge> srcEdges = pyanMetrics.getStaticEdges();
                Set<CallEdge> dstEdges = walaMetrics.getStaticEdges();
                if (pyanMetrics.getStaticEdges().size() > walaMetrics.getStaticEdges().size()) {
                    ExtractMetrics.compareEdgeSets(dstEdges, srcEdges, pyanWalaMetrics, true, false);
                } else {
                    ExtractMetrics.compareEdgeSets(srcEdges, dstEdges, pyanWalaMetrics, true, true);
                }
                metricsMap.get(lib).put(pyan_wala, pyanWalaMetrics);

                if (c2fMetrics != null) {
                    // Edges present in all three frameworks
                    FrameworkMetrics pyanWalaC2fMetrics = new FrameworkMetrics(pyan_wala_c2f, pyan_wala_c2f, lib);
                    pyanWalaC2fMetrics.setDynEdges(pyanMetrics.getDynEdges());
                    ExtractMetrics.compareEdgeSets(pyanWalaMetrics.getCommonEdges(), c2fMetrics.getStaticEdges(),
                            pyanWalaC2fMetrics, true, false);
                    pyanWalaC2fMetrics.setCommonEdges(pyanMetrics.getPresentDynEdges());
                    metricsMap.get(lib).put(pyan_wala_c2f, pyanWalaC2fMetrics);
                }
            }

            if (pyanMetrics != null && c2fMetrics != null) {
                FrameworkMetrics pyanC2fMetrics = new FrameworkMetrics(c2f_pyan, c2f_pyan, lib);
                pyanC2fMetrics.setDynEdges(pyanMetrics.getDynEdges());
                Set<CallEdge> srcEdges = pyanMetrics.getStaticEdges();
                Set<CallEdge> dstEdges = c2fMetrics.getStaticEdges();
                if (pyanMetrics.getStaticEdges().size() > c2fMetrics.getStaticEdges().size()) {
                    ExtractMetrics.compareEdgeSets(srcEdges, dstEdges, pyanC2fMetrics, false, true);
                } else {
                    ExtractMetrics.compareEdgeSets(dstEdges, srcEdges, pyanC2fMetrics, false, true);
                }
                metricsMap.get(lib).put(c2f_pyan, pyanC2fMetrics);
            }

            if (c2fMetrics != null && walaMetrics != null) {
                FrameworkMetrics walaC2fMetrics = new FrameworkMetrics(wala_c2f, wala_c2f, lib);
                walaC2fMetrics.setDynEdges(pyanMetrics.getDynEdges());
                Set<CallEdge> srcEdges = walaMetrics.getStaticEdges();
                Set<CallEdge> dstEdges = c2fMetrics.getStaticEdges();
                if (pyanMetrics.getStaticEdges().size() > c2fMetrics.getStaticEdges().size()) {
                    ExtractMetrics.compareEdgeSets(srcEdges, dstEdges, walaC2fMetrics, true, false);
                } else {
                    ExtractMetrics.compareEdgeSets(dstEdges, srcEdges, walaC2fMetrics, true, true);
                }
                metricsMap.get(lib).put(wala_c2f, walaC2fMetrics);
            }
        }
        return metricsMap;
    }
}
