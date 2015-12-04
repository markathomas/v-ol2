package org.vaadin.vol.client;

import com.vaadin.shared.communication.ServerRpc;

import java.util.Map;

public interface FeatureSelectionServerRpc extends ServerRpc {

    void beforeFeatureSelected(String fid, Map<String, Object> attr, String wkt);
    void featureSelected(String fid, Map<String, Object> attr, String wkt);
    void featureUnselected(String fid, Map<String, Object> attr, String wkt);
}
