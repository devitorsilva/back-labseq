package org.acme.Services;

import java.math.BigInteger;

import org.acme.DTO.LabseqResponsetDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LabseqService {
    CacheService cacheService = new CacheService();

    @Inject
    LabseqResponsetDTO labseqResponsetDTO;

    public LabseqResponsetDTO calculateLabseq(long number) {
        if (number == 0 || number == 2) {
            labseqResponsetDTO.setResult(BigInteger.ZERO.toString());
            return labseqResponsetDTO;
        } else if (number == 1 || number == 3) {
            labseqResponsetDTO.setResult(BigInteger.ONE.toString());
            return labseqResponsetDTO;
        }

        if (cacheService.size() <= 4) {
            setDefaultCache(cacheService);
        }

        if (cacheService.containsKey(number)) {
            labseqResponsetDTO.setError(null);
            labseqResponsetDTO.setResult(cacheService.get(number).toString());
            return labseqResponsetDTO;
        }

        //l(n) = l(n-4) + l(n-3)
        BigInteger l0, l1, l2, l3, result;

        long lastIndex = cacheService.size() -1;
        l0 = cacheService.get(lastIndex - 3);    //ln(-4)
        l1 = cacheService.get(lastIndex - 2);    //ln(-3)
        l2 = cacheService.get(lastIndex - 1);    //ln(-2)
        l3 = cacheService.get(lastIndex);        //ln(-1)
        result = l3;

        long startTime = System.currentTimeMillis();
        long timeLimit = 10_000L; // 10s
        for (long i = lastIndex + 1; i <= number; i++) {
            result = l0.add(l1);

            l0 = l1;
            l1 = l2;
            l2 = l3;
            l3 = result;

            cacheService.put(i, result);

            if ((System.currentTimeMillis() - startTime) > timeLimit) {
                labseqResponsetDTO.setError("Timeout reached, returning partial calculation up to n=" + i);
                labseqResponsetDTO.setResult(result.toString());
                return labseqResponsetDTO;
            }
        }
        labseqResponsetDTO.setError("");
        labseqResponsetDTO.setResult(result.toString());

        return labseqResponsetDTO;
    }

    void setDefaultCache(CacheService cache) {
        cache.put(0L, BigInteger.ZERO);
        cache.put(1L, BigInteger.ONE);
        cache.put(2L, BigInteger.ZERO);
        cache.put(3L, BigInteger.ONE);
    }

    public LabseqResponsetDTO cleanCache() {
        cacheService.clear();
        return labseqResponsetDTO;
    }

}




