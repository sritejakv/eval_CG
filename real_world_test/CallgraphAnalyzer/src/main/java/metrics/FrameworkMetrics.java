package metrics;

import com.ibm.wala.util.collections.HashSetFactory;
import probe.CallEdge;

import java.util.Set;

public class FrameworkMetrics {
    private String framework, cgAlgorithm, realWorldLib;
    private Set<CallEdge> dynEdges;
    private Set<CallEdge> staticEdges;
    private Set<CallEdge> commonEdges;
    private Set<CallEdge> presetDynEdges;

    public FrameworkMetrics(String cgFramework, String cgAlg, String lib) {
        framework = cgFramework;
        cgAlgorithm = cgAlg;
        realWorldLib = lib;
        commonEdges = HashSetFactory.make();
        staticEdges = HashSetFactory.make();
        presetDynEdges = HashSetFactory.make();
    }

    public Set<CallEdge> getPresentDynEdges() {
        return presetDynEdges;
    }

    public void addPresentDynEdge(CallEdge e) {
        this.presetDynEdges.add(e);
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getCgAlgorithm() {
        return cgAlgorithm;
    }

    public void setCgAlgorithm(String cgAlgorithm) {
        this.cgAlgorithm = cgAlgorithm;
    }

    public String getRealWorldLib() {
        return realWorldLib;
    }

    public void setRealWorldLib(String realWorldLib) {
        this.realWorldLib = realWorldLib;
    }

    public void addCommonEdge(CallEdge e) {
        commonEdges.add(e);
    }

    public void setCommonEdges(Set<CallEdge> cmnEdges) {
        this.commonEdges = cmnEdges;
    }

    public Set<CallEdge> getCommonEdges() {
        return commonEdges;
    }

    public Set<CallEdge> getDynEdges() {
        return dynEdges;
    }

    public void setDynEdges(Set<CallEdge> dynEdges) {
        this.dynEdges = dynEdges;
    }

    public Set<CallEdge> getStaticEdges() {
        return staticEdges;
    }

    public void setStaticEdges(Set<CallEdge> staticEdges) {
        this.staticEdges = staticEdges;
    }
}
