package com.ssafy.api.service;

import com.ssafy.db.entity.Files;
import com.ssafy.db.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FilesService {

    @Autowired
    FilesRepository filesRepository;

    @Transactional
    public void save(Files files){
        Files f=new Files();
        f.setFilename(files.getFilename());
        f.setFileOriName(files.getFileOriName());
        f.setFileUrl(files.getFileUrl());
        f.setTest(files.getTest());

        filesRepository.save(f);
    }
}