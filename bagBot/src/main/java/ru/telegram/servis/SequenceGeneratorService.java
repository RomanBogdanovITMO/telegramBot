package ru.telegram.servis;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.telegram.model.QuestionAndAnswer;
import ru.telegram.repository.QuestionAndAnswerRepository;

import java.util.List;


@Service
public class SequenceGeneratorService {
    @Autowired
    private QuestionAndAnswerRepository repository;


    //добовляем модель в бд
    public QuestionAndAnswer create(String question, String answer, String info) {
        QuestionAndAnswer questionAndAnswer = new QuestionAndAnswer();
        questionAndAnswer.setQuestion(question);
        questionAndAnswer.setAnswer(answer);
        questionAndAnswer.setAdditionInfo(info);
        return repository.save(questionAndAnswer);
    }

    //выбираем все модели в бд
    public List<QuestionAndAnswer> getAll() {
        List<QuestionAndAnswer> list= Lists.newArrayList(repository.findAll());

        return list;
    }

    /*//находим нужный обьект по вопросу, изменяем и обновляем бд
    public QuestionAndAnswer update(String question, String answer, String info) {
        QuestionAndAnswer questionAndAnswer = repository.findByQuestion(question);
        questionAndAnswer.setQuestion(question);
        questionAndAnswer.setAnswer(answer);
        questionAndAnswer.setAdditionInfo(info);
        return repository.save(questionAndAnswer);
    }*/

    //находим нужный обьект по ID, изменяем и обновляем бд.
    public QuestionAndAnswer updateById(int id, String question, String answer, String info) {
        QuestionAndAnswer questionAndAnswer = repository.findById(id).get();
        questionAndAnswer.setQuestion(question);
        questionAndAnswer.setAnswer(answer);
        questionAndAnswer.setAdditionInfo(info);
        return repository.save(questionAndAnswer);
    }

    //удалить все модели в бд
    public void deleteAll()
    {
        repository.deleteAll();
    }

    //удалить выбранную модель
    public void delete(int id) {
        QuestionAndAnswer questionAndAnswer = repository.findById(id).get();
        repository.delete(questionAndAnswer);
    }
}
