package Project.Ex.sign.sign_domain.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;


public class SignInCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession httpSession = request.getSession(false);

        //검증
        //컨트롤러에서 session.setAttribute("loginMember", loginMember);의 loginMember 비교
        if(httpSession==null || httpSession.getAttribute("loginMember") == null) {
            response.sendRedirect("/signIn?redirectURL=" + requestURI);
            return false;
        }

        //검증 통과 시 true
        return true;
    }
}
