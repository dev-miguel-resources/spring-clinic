package com.spring.service;

import com.spring.model.Exam;

public interface IExamService extends ICRUD<Exam, Integer> {

    // DRY PRINCIPLE: Don´t repeat yourself!
}
