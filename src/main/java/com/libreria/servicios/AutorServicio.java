package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional(rollbackFor = Exception.class)
    public void crear(String nombre) throws ErrorServicio {

        Autor autor = new Autor();

        validar(nombre);

        autor.setNombre(nombre);
        autor.setAlta(true);

        autorRepositorio.save(autor);

    }

    @Transactional(readOnly = true) // SOLO LEER EN BASE DE DATOS ------ No DEBERIA MODIFICAR NADA EN LA BASE , PERO SI POR ALGUN ERROR TIENE QUE MODIFICAR , TIRA ERROR Y NOS AVISA
    public Autor buscarPorId(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            return autor;

        } else {

            throw new ErrorServicio("NO EXISTE ESE AUTOR");
        }

    }

    @Transactional(readOnly = true)
    public List<Autor> listar() {
        return autorRepositorio.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void consultar(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            System.out.println(autor);

            autorRepositorio.save(autor);

        } else {

            throw new ErrorServicio(" No se encontro el Autor ");

        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void modificar(String id , String nombre) throws ErrorServicio {

      

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            
           autor.setNombre(nombre);

            autorRepositorio.save(autor);

        } else {

            throw new ErrorServicio(" No se encontro el Autor ");

        }
    }

    public void darDeBaja(String id) throws ErrorServicio {

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();

            if (autor.getAlta() == true) {
                autor.setAlta(false);
            } else {
                autor.setAlta(true);
            }
            autorRepositorio.save(autor);

        } else {

            throw new ErrorServicio(" No se encontro el Autor ");

        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void eliminar(String id) throws ErrorServicio {

        Optional<Autor> autorRespuesta = autorRepositorio.findById(id);
        List<Libro> libroRespuesta = libroRepositorio.buscarPorIdAutor(id);
        
        if (autorRespuesta.isPresent()) {
            if (libroRespuesta.isEmpty()) {
                autorRepositorio.deleteById(autorRespuesta.get().getId());
            }else{
                throw new ErrorServicio("ERROR ! El autor tiene uno o mas Libros asignados ");
            }

        } else {

            throw new ErrorServicio(" No se encontro el Autor , o tiene tiene asignado un Libro ");

        }
    }

    private void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {

            throw new ErrorServicio(" El nombre de el Autor no puede ser Nulo. ");

        }

    }

}
