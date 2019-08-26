package mySpring;

import lombok.Getter;

import java.util.Map;

public interface Config {
    <T> Class<T> getImplClass(Class<T> type);
    String getPackageToScan();
}
