package com.robcio.ccnotepad.repository;

import com.robcio.ccnotepad.entity.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends CrudRepository<Setting, String> {

    Optional<Setting> findByName(String name);
}
