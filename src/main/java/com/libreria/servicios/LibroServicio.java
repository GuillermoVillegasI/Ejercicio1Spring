package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional(rollbackFor = Exception.class) // MODIFICA LA BASE , SI HAY ERROR EN LA MODIFICACION TIRA EXCEPTION
    public Libro crear(String id, long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, boolean alta, String idAutor, String idEditorial) throws ErrorServicio {

        Libro libro = new Libro();
        validar(titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(alta);

        Editorial editorial = editorialServicio.buscarPorId(idEditorial);
        Autor autor = autorServicio.buscarPorId(idAutor);

        libro.setEditorial(editorial);
        libro.setAutor(autor);

        return libroRepositorio.save(libro);

    }

    @Transactional(readOnly = true) // SOLO LEER EN BASE DE DATOS ------ No DEBERIA MODIFICAR NADA EN LA BASE , PERO SI POR ALGUN ERROR TIENE QUE MODIFICAR , TIRA ERROR Y NOS AVISA
    public Libro buscarPorId(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            return libro;

        } else {

            throw new ErrorServicio("NO EXISTE ESE LIBRO");
        }

    }
    
    public List<Libro> buscarPorTitulo (String titulo){
        
        return libroRepositorio.buscarPorTitulo(titulo);
        
    }

    @Transactional(rollbackFor = Exception.class)
    public void consultar(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            System.out.println(libro);

            libroRepositorio.save(libro);

        } else {

            throw new ErrorServicio(" No se encontro la Libro ");

        }

    }

    public void modificar(String id, String titulo, boolean alta) throws ErrorServicio {

        validarTitulo(titulo);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAlta(alta);

            libroRepositorio.save(libro);

        } else {

            throw new ErrorServicio(" No se encontro la Libro ");

        }
    }

    public void elimninar(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libroRepositorio.deleteById(id);

            libroRepositorio.save(libro);

        } else {

            throw new ErrorServicio(" No se encontro la Libro ");

        }
    }

    public void darDeBaja(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.setAlta(false);

            libroRepositorio.save(libro);

        } else {

            throw new ErrorServicio(" No se encontro la Libro ");

        }

    }

    private void validar(String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty()) {

            throw new ErrorServicio(" El nombre de la Libro no puede ser Nulo. ");

        }
        if (anio == null || anio < 0) {

            throw new ErrorServicio(" El año tiene un valor incorrecto ");

        }
        if (ejemplares == null || anio < 0) {

            throw new ErrorServicio(" El nombre de la Libro no puede ser Nulo. ");

        }
        if (ejemplaresPrestados == null || anio < 0) {

            throw new ErrorServicio(" El nombre de la Libro no puede ser Nulo. ");

        }
        if (ejemplaresRestantes == null || anio < 0) {

            throw new ErrorServicio(" El nombre de la Libro no puede ser Nulo. ");

        }

    }

    private void validarTitulo(String titulo) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty()) {

            throw new ErrorServicio(" El nombre de la Libro no puede ser Nulo. ");

        }

    }
}
