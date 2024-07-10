//package project.boot.fideco.controller;
//
//import java.security.Principal;
//
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import project.boot.fideco.dto.AnswerDTO;
//import project.boot.fideco.dto.QuestionDTO;
//import project.boot.fideco.entity.Question;
//import project.boot.fideco.entity.Users;
//import project.boot.fideco.service.QuestionService;
//import project.boot.fideco.service.UserService;
//
//@RequestMapping("/question")
//@RequiredArgsConstructor
//@Controller
//public class QuestionController {
//
//    private final QuestionService questionService;
//    private final UserService userService;
//
//    @GetMapping("/list")
//    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
//                       @RequestParam(value = "kw", defaultValue = "") String kw) {
//        Page<Question> paging = this.questionService.getList(page, kw);
//        model.addAttribute("paging", paging);
//        model.addAttribute("kw", kw);
//        return "question_list";
//    }
//
//    @GetMapping(value = "/detail/{id}")
//    public String detail(Model model, @PathVariable("id") Integer id, AnswerDTO answerDTO) {
//        Question question = this.questionService.getQuestion(id);
//        model.addAttribute("question", question);
//        return "question_detail";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/create")
//    public String questionCreate(QuestionDTO questionDTO) {
//        return "question_form";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/create")
//    public String questionCreate(@Valid QuestionDTO questionDTO, BindingResult bindingResult, Principal principal,
//                                 @RequestParam("file") MultipartFile file) throws Exception {
//        if (bindingResult.hasErrors()) {
//            return "question_form";
//        }
//        Users users = this.userService.getUser(principal.getName());
//        this.questionService.create(questionDTO.getSubject(), questionDTO.getContent(), users, file);
//        return "redirect:/question/list";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/modify/{id}")
//    public String questionModify(QuestionDTO questionDTO, @PathVariable("id") Integer id, Principal principal) {
//        Question question = this.questionService.getQuestion(id);
//        if (!question.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//        questionDTO.setSubject(question.getSubject());
//        questionDTO.setContent(question.getContent());
//        return "question_form";
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/modify/{id}")
//    public String questionModify(@Valid QuestionDTO questionDTO, BindingResult bindingResult, Principal principal,
//                                 @PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws Exception {
//        if (bindingResult.hasErrors()) {
//            return "question_form";
//        }
//        Question question = this.questionService.getQuestion(id);
//        if (!question.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//        }
//        this.questionService.modify(question, questionDTO.getSubject(), questionDTO.getContent(), file);
//        return String.format("redirect:/question/detail/%s", id);
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/delete/{id}")
//    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
//        Question question = this.questionService.getQuestion(id);
//        if (!question.getAuthor().getUsername().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//        }
//        this.questionService.delete(question);
//        return "redirect:/";
//    }
//}
