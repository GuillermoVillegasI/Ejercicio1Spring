package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.errores.ErrorServicio;
import com.libreria.servicios.AutorServicio;
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
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/autor")
    public String autor() {
        return "autor.html";
    }

    @GetMapping("/autor/listarAutor")
    public String listarAutor(ModelMap modelo) {

        List<Autor> autores = autorServicio.listar();
        modelo.put("autores", autores);

        return "listarAutor.html";
    }

    @PostMapping("/cargarAutor")
    public String cargarAutor(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio {
        try {
            autorServicio.crear(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("Error", ex.getMessage());
            return "autor.html";
        }
        modelo.put("descripcion", "El autor fue registrado correctamente");
        return "autor.html";
    }

    @GetMapping("/cambiarEstadoAutor")
    public String cambiarEstadoAutor(RedirectAttributes attr, @RequestParam String id) throws ErrorServicio {
        try {
            autorServicio.darDeBaja(id);
            attr.addFlashAttribute("descripcion", "El ha cambiado su estado");
        } catch (ErrorServicio ex) {
            attr.addFlashAttribute("Error", ex.getMessage());
        }

        return "redirect:/autor/listarAutor";
    }

    @GetMapping("/eliminarAutor/{id}")
    public String eliminarAutor(RedirectAttributes attr, @PathVariable("id") String id) throws ErrorServicio {
        try {
            autorServicio.eliminar(id);
            attr.addFlashAttribute("descripcion", "El autor ha sido eliminado correctamente");
        } catch (ErrorServicio ex) {
            attr.addFlashAttribute("Error", ex.getMessage());
        }

        return "redirect:/autor/listarAutor";
    }

    @GetMapping("/modificarAutor")
    public String modificarAutor(ModelMap modelo, @RequestParam String id) throws ErrorServicio {
        try {
            Autor autor = autorServicio.buscarPorId(id);
            modelo.addAttribute("autor", autor);
        } catch (ErrorServicio ex) {
            modelo.put("Error", ex.getMessage());
        }
        return "modificarAutor.html";
    }

    @PostMapping("/actualizarAutor")
    public String actualizarAutor(RedirectAttributes attr, @RequestParam String id, @RequestParam String nombre) throws ErrorServicio {
        try {
            Autor autor = autorServicio.buscarPorId(id);
            autorServicio.modificar(id, nombre);

        } catch (ErrorServicio ex) {
             attr.addFlashAttribute("Error", ex.getMessage());
            return "listarAutor.html";
        }
         attr.addFlashAttribute("descripcion", "El autor fue modificado correctamente");
        return "redirect:/autor/listarAutor";

    }
}
