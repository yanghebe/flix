package ca.uwaterloo.flix.relational

import java.sql._
import java.util.Enumeration
import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.h2.Driver

class H2Test extends FunSuite {
    def asScalaCollection[T](e: Enumeration[T]): Seq[T] = {
        var ret: List[T] = List()
        while (e.hasMoreElements()) {
            ret :+= e.nextElement()
        }
        ret
    }

    test("basic functionality of H2") {
        val c = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
        val stmt = c.createStatement();
        stmt.execute("CREATE TABLE PERSON(id int primary key, name varchar(255))");
        stmt.execute("INSERT INTO PERSON(id, name) VALUES(1, 'Anju')");
        stmt.execute("INSERT INTO PERSON(id, name) VALUES(2, 'Sonia')");
        stmt.execute("INSERT INTO PERSON(id, name) VALUES(3, 'Asha')");
    
        val rs = stmt.executeQuery("select id + 2 id, name from PERSON");
        rs.next(); rs.getInt("id") shouldEqual 3; rs.getString("name") shouldEqual "Anju";
        rs.next(); rs.getInt("id") shouldEqual 4; rs.getString("name") shouldEqual "Sonia";
        rs.next(); rs.getInt("id") shouldEqual 5; rs.getString("name") shouldEqual "Asha";        
    }
}