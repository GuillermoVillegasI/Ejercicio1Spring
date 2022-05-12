package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
import com.libreria.servicios.EditorialServicio;
import com.libreria.servicios.LibroServicio;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class PortalControlador {

    @GetMapping
    public String index() {
        return "index.html";
    }

//    @GetMapping("/libro")
//    public String libro() {
//        return "libro.html";
//    }
//
//    @GetMapping("/autor")
//    public String autor() {
//        return "autor.html";
//    }
//
//    @GetMapping("/editorial")
//    public String editorial() {
//        return "editorial.html";
//    }

//    //   ---------------- LIBRO -------------------
//    @PostMapping("/cargar")
//
//    public String cargar(@RequestParam long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares) {
//
//        return "libro.html";
//    }
//
//    //   ---------------- AUTOR -------------------
//    @PostMapping("/cargarAutor")
//
//    public String cargarAutor(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio {
//        try {
//            autorServicio.crear(nombre);
//        } catch (ErrorServicio ex) {
//            modelo.put("Error", ex.getMessage());
//            return "autor.html";
//        }
//        modelo.put("descripcion", "El autor fue registrado correctamente");
//        return "autor.html";
//    }
//    
//    
//
//    //   ---------------- EDITORIAL -------------------
//    @PostMapping("/cargarEditorial")
//
//    public String cargarEditorial(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio {
//        try {
//            editorialServicio.crear(nombre);
//        } catch (ErrorServicio ex) {
//            modelo.put("Error", ex.getMessage());
//            return "editorial.html";
//        }
//        modelo.put("descripcion", "La editorial fue registrado correctamente");
//        return "editorial.html";
//    }

}
