package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
import com.libreria.servicios.EditorialServicio;
import com.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/libro")
    public String libro(ModelMap modelo) {

        List<Editorial> editoriales = editorialServicio.listar();
        List<Autor> autores = autorServicio.listar();

        modelo.put("editoriales", editoriales);
        modelo.put("autores", autores);

        return "libro.html";
    }

    @PostMapping("/cargar")
    public String cargar(ModelMap modelo, @RequestParam long isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial) throws ErrorServicio {
        try {
            libroServicio.crear(isbn, titulo, anio, ejemplares, idAutor, idEditorial);
        } catch (ErrorServicio ex) {
            modelo.put("Error", ex.getMessage());
            return "libro.html";
        }
        modelo.put("descripcion", "El libro fue registrado correctamente");
        return "/libro.html";
    }

    @GetMapping("/modificarLibro/{id}")
    public String modificarLibro(@PathVariable("id") String id ,  ModelMap modelo ) throws ErrorServicio {

        try {
            Libro libro = libroServicio.buscarPorId(id);
            List<Editorial> editoriales = editorialServicio.listar();
            List<Autor> autores = autorServicio.listar();

            modelo.addAttribute("editoriales", editoriales);
            modelo.addAttribute("autores", autores);

            modelo.put("libro", libro);
        } catch (ErrorServicio ex) {
            modelo.put("Error", ex.getMessage());
        }
        return "/modificarLibro.html";
    }

    @PostMapping("/actualizarLibro")
    public String actualizarLibro(RedirectAttributes attr, @RequestParam String id, @RequestParam long isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial) throws ErrorServicio {

        try {
            Libro libro = libroServicio.buscarPorId(id);
            libroServicio.modificar(id, isbn, titulo, anio, ejemplares, idAutor, idEditorial);

        } catch (ErrorServicio ex) {
            attr.addFlashAttribute("Error", ex.getMessage());
            return "redirect:/libro/listarLibro";
        }
        attr.addFlashAttribute("descripcion", "El libro fue modificado correctamente");
        return "redirect:/libro/listarLibro";

    }

    @GetMapping("/libro/listarLibro")
    public String listarLibro(ModelMap modelo) {

        List<Libro> libros = libroServicio.listar();
        modelo.put("libros", libros);

        return "listarLibro.html";
    }

    @GetMapping("/cambiarEstadoLibro")
    public String cambiarEstadoLibro(RedirectAttributes attr, @RequestParam String id) throws ErrorServicio {
        try {
            libroServicio.darDeBaja(id);
            attr.addFlashAttribute("descripcion", "El libro ha cambiado su estado");
        } catch (ErrorServicio ex) {
            attr.addFlashAttribute("Error", ex.getMessage());
        }

        return "redirect:/libro/listarLibro";
    }

    @GetMapping("/eliminarLibro/{id}")
    public String eliminarlibro(RedirectAttributes attr, @PathVariable("id") String id) throws ErrorServicio {
        try {
            libroServicio.eliminar(id);
            attr.addFlashAttribute("descripcion", "El libro ha sido eliminado correctamente");
        } catch (ErrorServicio ex) {
            attr.addFlashAttribute("Error", ex.getMessage());
        }

        return "redirect:/libro/listarLibro";
    }

}
