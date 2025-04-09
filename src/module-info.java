module Restaurante {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
<<<<<<< HEAD
	requires javafx.base;
	requires org.controlsfx.controls;
=======
>>>>>>> branch 'master' of https://github.com/xp-tatsuya/Restaurante.git
	
	opens application to javafx.graphics, javafx.fxml;
<<<<<<< HEAD
	opens Controller to javafx.graphics, javafx.fxml;
	opens Model to javafx.graphics, javafx.fxml, javafx.base;
=======
	opens ConnectionFactory to javafx.fxml, javafx.base;
	opens Controller to javafx.graphics, javafx.fxml;
>>>>>>> branch 'master' of https://github.com/xp-tatsuya/Restaurante.git
}
