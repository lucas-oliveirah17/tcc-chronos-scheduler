import React from 'react';

export function TabelaModular({ colunasMapeadas, dados, onEdit, onDelete }) {
  if (!dados || dados.length === 0) {
    return <p>Nenhum dado encontrado.</p>;
  }
  
  const headers = Object.keys(colunasMapeadas);
  const chaves = Object.values(colunasMapeadas);

  return (
    <table className="tabela-admin">
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
            {chaves.map((chave) => (
              <td key={`${item.id}-${chave}`}>{item[chave]}</td>
            ))}
            {(onEdit || onDelete) && (
              <td className="tabela-acoes">
                {onEdit && (
                  <button onClick={() => onEdit(item.id)} className="btn-editar">
                    <i className="fa fa-fw fa-pencil"></i>
                  </button>
                )}
                {onDelete && (
                  <button onClick={() => onDelete(item.id)} className="btn-deletar">
                    <i className="fa fa-fw fa-trash"></i>
                  </button>
                )}
              </td>
            )}
          </tr>
        ))}
      </tbody>
    </table>
  );
}