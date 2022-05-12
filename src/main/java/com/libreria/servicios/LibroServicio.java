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
    public Libro crear(long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio {

        Libro libro = new Libro();
        validar(isbn, titulo, anio, ejemplares, idAutor, idEditorial);

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setAlta(true);

        Editorial editorial = editorialServicio.buscarPorId(idEditorial);
        Autor autor = autorServicio.buscarPorId(idAutor);

        libro.setEditorial(editorial);
        libro.setAutor(autor);

        return libroRepositorio.save(libro);

    }

    @Transactional(rollbackFor = Exception.class)
    public void modificar(String id, long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio {
        validarTitulo(titulo);
        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();

            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
           
            
            Editorial editorial = editorialServicio.buscarPorId(idEditorial);
            Autor autor = autorServicio.buscarPorId(idAutor);

            libro.setEditorial(editorial);
            libro.setAutor(autor);

            libroRepositorio.save(libro);

        } else {

            throw new ErrorServicio(" No se encontro el Libro ");

        }
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

    @Transactional(readOnly = true)
    public List<Libro> listar() {
        return libroRepositorio.findAll();
    }

    public List<Libro> buscarPorTitulo(String titulo) {

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

    @Transactional(rollbackFor = Exception.class)
    public void eliminar(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            libroRepositorio.deleteById(respuesta.get().getId());

        } else {

            throw new ErrorServicio(" No se encontro el Libro ");

        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void eliminarPorAutor(String id) throws ErrorServicio {

        List<Libro> libros = listar();

        for (Libro l : libros) {
            if (l.getAutor().getId() == id) {
                l.getId();
                libroRepositorio.deleteById(l.getId());

            } else {

                throw new ErrorServicio(" No se encontro el Libro ");

            }

        }

    }

    public void darDeBaja(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();

            if (libro.getAlta() == true) {
                libro.setAlta(false);
            } else {
                libro.setAlta(true);
            }
            libroRepositorio.save(libro);

        } else {

            throw new ErrorServicio(" No se encontro el Libro ");

        }

    }

    private void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio {
        if (isbn.toString() == null || isbn <= 0) {
            throw new ErrorServicio(" El isbn del Libro no puede ser Nulo. ");

        }
        if (titulo == null || titulo.isEmpty()) {

            throw new ErrorServicio(" El nombre del Libro no puede ser Nulo. ");

        }
        if (anio == null || anio < 0) {

            throw new ErrorServicio(" El año tiene un valor incorrecto ");

        }
        if (ejemplares == null || ejemplares < 0) {

            throw new ErrorServicio(" El nombre del Libro no puede ser Nulo. ");

        }
        if (idAutor == null) {

            throw new ErrorServicio(" El autor del Libro no es correcto. ");

        }
        if (idEditorial == null) {

            throw new ErrorServicio(" La editorial del Libro no es correcta. ");
        }
    }

    private void validarTitulo(String titulo) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty()) {

            throw new ErrorServicio(" El nombre de la Libro no puede ser Nulo. ");

        }

    }
}
