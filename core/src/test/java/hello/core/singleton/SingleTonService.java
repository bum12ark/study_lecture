package hello.core.singleton;

public class SingleTonService {

    // 1. java 가 뜰 때 객체를 생성 한다. (static 영역)
    private static final SingleTonService instance = new SingleTonService();

    // 2. 객체 인스턴스가 필요할 때는 아래의 메소드를 통해 조회하도록 허용한다.
    public static SingleTonService getInstance() {
        return instance;
    }

    // 3. 객체 외부 생성 방지
    private SingleTonService() {};

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
