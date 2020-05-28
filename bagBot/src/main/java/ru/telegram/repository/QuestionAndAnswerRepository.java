package ru.telegram.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.telegram.model.QuestionAndAnswer;


@Repository
public interface QuestionAndAnswerRepository extends CrudRepository<QuestionAndAnswer,Integer> {

}
