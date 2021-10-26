## 프로젝트 세팅

### 프로젝트 환경

- Gradle project
- Java Version: 11
- Database: h2 database

### 의존성 설정

```
plugins {
    id 'java'
}

group 'ex1-hello-jpa'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.6.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'

    // JPA 하이버 네이트
    implementation 'org.hibernate:hibernate-entitymanager:5.5.7.Final'

    // H2 데이터베이스
    implementation 'com.h2database:h2:1.4.199'

    // assertj (assertThat 사용 위해)
    testImplementation 'org.assertj:assertj-core:3.19.0'

}
test {
    useJUnitPlatform()
}
```

- **JPA 하이버네이트**
- **H2 데이터베이스**
- **assertJ, JUnit**: 테스트 환경 세팅

### JPA 데이터베이스 관련 설정하기

- resource/META-INF/persistence.xml 파일 생성

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    <persistence-unit name="hello">
        <properties>
            <!-- 필수 속성 -->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>

            <!-- 옵션 -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.use_sql_comments" value="true"/>
            <!--<property name="hibernate.hbm2ddl.auto" value="create" />-->
        </properties>
    </persistence-unit>
</persistence>
```

### 기본 테스트

```java
package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.close();
        emf.close();
    }

}
```

```java
package hello.jpa;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JpaMainTest {

    EntityManagerFactory emf;

    @BeforeAll
    void beforeAll() {
        emf = Persistence.createEntityManagerFactory("hello");
    }

    @DisplayName("Jpa 기본 실행 테스트")
    @Test
    void jpaBasicImplement() {
        EntityManager entityManager = emf.createEntityManager();

        boolean open = entityManager.isOpen();

        assertThat(open).isTrue();

        entityManager.close();
    }

    @AfterAll
    void afterAll() {
        emf.close();
    }
}
```

- @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    - @BeforAll과 @AfterAll은 static으로 사용해야하지만, 해당 애노테이션을 통해 static을 붙히지 않아도 됨

### Member 테이블 생성

```sql
create table Member (
		id bigint not null,
		name varchar(255),
		primary key (id)
);
```

### java.lang.IllegalArgumentException: Unknown entity

- Spring 없이 Gradle 환경에서 JPA 사용시 entity 클래스를 찾지 못하는 문제점 발생
- persistence.xml 파일에 class 태그를 사용하여 entity를 추가

```xml
<persistence-unit name="hello">
    <class>hello.jpa.entity.Member</class>
    <properties>
        <!-- 필수 속성 -->
        <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
				...
```