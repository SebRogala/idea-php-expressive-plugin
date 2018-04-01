<?php

namespace {{ ModuleName }}\Service;

use Doctrine\ORM\EntityManager;
use Xsv\Doctrine\Hydrator\Hydrator;

class Read{{ ResourceName }}DoctrineService
{
    /** @var EntityManager */
    protected $entityManager;

    /**
     * Create{{ ResourceName }}DoctrineService constructor.
     * @param EntityManager $entityManager
     */
    public function __construct(EntityManager $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    public function find($queryParams)
    {
        $queryString = "
            SELECT x
            FROM {{ ModuleName }}\Entity\{{ ResourceName }} x
            WHERE x.name LIKE :phrase
        ";
        $query = $this->entityManager->createQuery($queryString);
        $query->setParameters(['phrase' => "%" . $queryParams['s'] . "%"]);

        return $query->getArrayResult();
    }

    public function findById($id)
    {
        $found{{ ResourceName }} = $this->entityManager->getRepository('{{ ModuleName }}\Entity\{{ ResourceName }}')->findById($id);

        return $found{{ ResourceName }};
    }
}
