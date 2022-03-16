package com.libreria.servicios;

import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorios.EditorialRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional(rollbackFor = Exception.class)
    public void crear(String nombre, boolean alta) throws ErrorServicio {

        Editorial editorial = new Editorial();

        validar(nombre);

        editorial.setNombre(nombre);
        editorial.setAlta(alta);

        editorialRepositorio.save(editorial);

    }

    @Transactional(readOnly = true) // SOLO LEER EN BASE DE DATOS ------ No DEBERIA MODIFICAR NADA EN LA BASE , PERO SI POR ALGUN ERROR TIENE QUE MODIFICAR , TIRA ERROR Y NOS AVISA
    public Editorial buscarPorId(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            return editorial;

        } else {

            throw new ErrorServicio("NO EXISTE ESE EDITORIAL");
        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void consultar(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            System.out.println(editorial);

            editorialRepositorio.save(editorial);

        } else {

            throw new ErrorServicio(" No se encontro la Editorial ");

        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void modificar(String id, String nombre, boolean alta) throws ErrorServicio {

        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorial.setAlta(alta);

            editorialRepositorio.save(editorial);

        } else {

            throw new ErrorServicio(" No se encontro la Editorial ");

        }
    }
    public void darDeBaja(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setAlta(false);

            editorialRepositorio.save(editorial);

        } else {

            throw new ErrorServicio(" No se encontro la Editorial ");

        }

    }
    @Transactional(rollbackFor = Exception.class)
    public void elimninar(String id) throws ErrorServicio {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorialRepositorio.deleteById(id);

            editorialRepositorio.save(editorial);

        } else {

            throw new ErrorServicio(" No se encontro la Editorial ");

        }
    }

    private void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {

            throw new ErrorServicio(" El nombre de la Editorial no puede ser Nulo. ");

        }

    }

}
