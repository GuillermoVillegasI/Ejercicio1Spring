package com.libreria.controladores;

import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.EditorialServicio;
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
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/editorial")
    public String editorial() {
        return "editorial.html";
    }

    @PostMapping("/cargarEditorial")
    public String cargarEditorial(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio {
        try {
            editorialServicio.crear(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("Error", ex.getMessage());
            return "editorial.html";
        }
        modelo.put("descripcion", "La editorial fue registrado correctamente");
        return "editorial.html";
    }

    @GetMapping("/editorial/listarEditorial")
    public String listarEditorial(ModelMap modelo) {

        List<Editorial> editoriales = editorialServicio.listar();
        modelo.put("editoriales", editoriales);

        return "listarEditorial.html";
    }
    


    @GetMapping("/eliminarEditorial/{id}")
    public String eliminarEditorial(RedirectAttributes attr, @PathVariable("id") String id) throws ErrorServicio {
        try {
            editorialServicio.eliminar(id);
            attr.addFlashAttribute("descripcion", "La editorial ha sido eliminada correctamente");
        } catch (ErrorServicio ex) {
            attr.addFlashAttribute("Error", ex.getMessage());
        }

        return "redirect:/editorial/listarEditorial";
    }
    
        @GetMapping("/modificarEditorial")
    public String modificarEditorial(ModelMap modelo, @RequestParam String id) throws ErrorServicio {
        try {
            Editorial editorial = editorialServicio.buscarPorId(id);
            modelo.addAttribute("editorial", editorial);
        } catch (ErrorServicio ex) {
            modelo.put("Error", ex.getMessage());
        }
        return "modificarEditorial.html";
    }

    @PostMapping("/actualizarEditorial")
    public String actualizarEditorial(RedirectAttributes attr, @RequestParam String id, @RequestParam String nombre) throws ErrorServicio {
        try {
            Editorial editorial = editorialServicio.buscarPorId(id);
            editorialServicio.modificar(id, nombre);

        } catch (ErrorServicio ex) {
             attr.addFlashAttribute("Error", ex.getMessage());
            return "listarEditorial.html";
        }
         attr.addFlashAttribute("descripcion", "La editorial fue modificada correctamente");
        return "redirect:/editorial/listarEditorial";

    }

}
