import React from 'react';
import { Edit2, Trash2 } from 'lucide-react';
import './TabelaModular.css';

export function TabelaModular({ colunasMapeadas, dados, onEdit, onDelete }) {
  if (!dados || dados.length === 0) {
    return (
      <div className="empty-state">
        <p>Nenhum dado encontrado.</p>
      </div>
    );
  }

  const headers = Object.keys(colunasMapeadas);
  const chaves = Object.values(colunasMapeadas);

  return (
    <div className="tabela-container">
      <table className="tabela-admin mobile-card-view">
        <thead>
          <tr>
            {headers.map((header) => (
              <th key={header}>{header}</th>
            ))}
            {(onEdit || onDelete) && <th>Ações</th>}
          </tr>
        </thead>
        <tbody>
          {dados.map((item) => (
            <tr key={item.id}>
              {headers.map((header, index) => (
                <td key={`${item.id}-${chaves[index]}`} data-label={header}>
                  {item[chaves[index]]}
                </td>
              ))}
              {(onEdit || onDelete) && (
                <td className="tabela-acoes" data-label="Ações">
                  <div className="acoes-container">
                    {onEdit && (
                      <button onClick={() => onEdit(item.id)} className="btn-icon btn-editar" title="Editar">
                        <Edit2 size={18} />
                      </button>
                    )}
                    {onDelete && (
                      <button onClick={() => onDelete(item.id)} className="btn-icon btn-deletar" title="Deletar">
                        <Trash2 size={18} />
                      </button>
                    )}
                  </div>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}