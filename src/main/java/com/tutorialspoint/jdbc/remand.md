<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/quanlygara?serverTimezone=UTC"/>
            <property name="jakarta.persistence.jdbc.user" value="root"/>
            <property name="jakarta.persistence.jdbc.password" value="thvvmd1012@"/>
       sửa lại trong file persistence.xml
muốn xóa dữ liệu các bảng khác phải xóa từ bảng hóa đơn trước, và muốn sửa trạng thái bảng
hóa đơn phải sửa lại ngày (không để null khi sửa)
tools\apache-maven-3.9.9\bin\mvn.cmd clean install
tools\apache-maven-3.9.9\bin\mvn.cmd exec:java