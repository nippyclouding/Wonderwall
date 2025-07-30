package Project.Ex.service;

import Project.Ex.domain.Member;
import Project.Ex.domain.Post;
import Project.Ex.domain.PostForm;
import Project.Ex.repository.MemberRepository;
import Project.Ex.repository.PostingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class PostingService {
    private final PostingRepository postingRepository;
    private final MemberRepository memberRepository;

    public Long savePost(PostForm postForm, Long memberSerialId){
        try {
            //사용자 신원 확인
            Member member = memberRepository.findById(memberSerialId).orElse(null);

            if (member == null) {
                throw new RuntimeException("존재하지 않는 사용자입니다.");
            }

            //PostForm 에서 데이터 조회, Post 생성, 저장
            Post post = new Post(postForm.getTitle(), postForm.getText(), member);
            Post savedPost = postingRepository.save(post);
            return savedPost.getPostingId();

        } catch (Exception e) {
            log.error("게시글 저장 실패: " + e.getMessage());
            throw new RuntimeException("게시글 저장에 실패했습니다.");
        }
    }
}
