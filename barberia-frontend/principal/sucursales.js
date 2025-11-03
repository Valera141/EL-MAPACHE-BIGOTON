// API Base URL
const API_BASE_URL = 'http://localhost:8081/api';

// Inicializar cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
    cargarSucursales();
    configurarFormulario();
});

// Configurar el listener del formulario
function configurarFormulario() {
    const form = document.getElementById('sucursalForm');
    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Evitar envío tradicional
        registrarSucursal();
    });
}

// Cargar y mostrar la lista de sucursales
async function cargarSucursales() {
    const listaContainer = document.getElementById('listaSucursales');
    listaContainer.innerHTML = '<div class="text-center"><div class="loading"></div><p>Cargando...</p></div>';

    try {
        const response = await fetch(`${API_BASE_URL}/sucursales`);
        if (!response.ok) throw new Error('Error al cargar sucursales');

        const sucursales = await response.json();

        if (sucursales.length === 0) {
            listaContainer.innerHTML = '<p class="text-center text-muted">No hay sucursales registradas.</p>';
            return;
        }

        listaContainer.innerHTML = sucursales.map(sucursal => `
            <div class="sucursal-item">
                <div class="sucursal-info">
                    <strong>${sucursal.nombre}</strong>
                    <small>${sucursal.direccion}</small>
                </div>
                <button class="btn btn-sm btn-danger" onclick="eliminarSucursal(${sucursal.id})" title="Eliminar">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        `).join('');

    } catch (error) {
        console.error('Error:', error);
        listaContainer.innerHTML = '<p class="text-center text-danger">Error al cargar la lista.</p>';
    }
}

// Registrar una nueva sucursal
async function registrarSucursal() {
    const nombre = document.getElementById('nombreSucursal').value.trim();
    const direccion = document.getElementById('direccionSucursal').value.trim();
    const btnGuardar = document.getElementById('btnGuardar');

    if (!nombre || !direccion) {
        alert('Por favor, complete todos los campos.');
        return;
    }

    const sucursalData = {
        nombre: nombre,
        direccion: direccion
    };

    try {
        btnGuardar.disabled = true;
        btnGuardar.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Guardando...';

        const response = await fetch(`${API_BASE_URL}/sucursales`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(sucursalData)
        });

        if (!response.ok) throw new Error('Error al registrar la sucursal');

        // Éxito
        alert('¡Sucursal registrada exitosamente!');
        document.getElementById('sucursalForm').reset(); // Limpiar formulario
        cargarSucursales(); // Recargar la lista

    } catch (error) {
        console.error('Error:', error);
        alert('Error al registrar la sucursal. Intente de nuevo.');
    } finally {
        btnGuardar.disabled = false;
        btnGuardar.innerHTML = '<i class="fas fa-save"></i> Guardar Sucursal';
    }
}

// Eliminar una sucursal
async function eliminarSucursal(id) {
    if (!confirm('¿Está seguro de que desea eliminar esta sucursal? Esta acción no se puede deshacer.')) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/sucursales/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Error al eliminar');

        // Éxito
        alert('Sucursal eliminada.');
        cargarSucursales(); // Recargar la lista

    } catch (error) {
        console.error('Error:', error);
        alert('Error al eliminar la sucursal. Es posible que tenga citas asociadas.');
    }
}