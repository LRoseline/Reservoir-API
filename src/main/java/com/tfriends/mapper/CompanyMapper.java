package com.tfriends.mapper;

import com.tfriends.domain.CompanyVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyMapper {
    public int countcomp();

    public CompanyVO checkinc(@Param("no") int no);

    public CompanyVO findinc(@Param("company") String company);

    public void addinc(CompanyVO vo);

    public int addtype(CompanyVO vo);
}
