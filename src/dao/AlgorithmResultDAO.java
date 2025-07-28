
package dao;

import java.util.List;

import models.AlgorithmResult;

public interface AlgorithmResultDAO {
    void save(AlgorithmResult resultado);
    List<AlgorithmResult> findAll();
    void clear();
}