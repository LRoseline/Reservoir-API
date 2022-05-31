package com.tfriends.service;

import com.tfriends.domain.CompanyVO;
import com.tfriends.mapper.CompanyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyMapper m;

    public int CompanyTotal() {
        return m.countcomp();
    }

    public CompanyVO IncCheck(int no) {
        return m.checkinc(no);
    }

    public CompanyVO IncFind(String company) {
        return m.findinc(company);
    }

    public void IncAdd(CompanyVO vo) {
        m.addinc(vo);
    }

    public boolean TypeAdd(CompanyVO vo) {
        return m.addtype(vo) == 1;
    }
}
