<?php

namespace {{ ModuleName }}\Service;

use Doctrine\ORM\EntityManager;
use Xsv\Doctrine\Hydrator\Hydrator;

class Update{{ ResourceName }}DoctrineService
{
    /** @var EntityManager */
    protected $entityManager;

    /** @var Hydrator */
    protected $hydrator;

    /**
     * Update{{ ResourceName }}DoctrineService constructor.
     * @param EntityManager $entityManager
     * @param Hydrator $hydrator
     */
    public function __construct(EntityManager $entityManager, Hydrator $hydrator)
    {
        $this->entityManager = $entityManager;
        $this->hydrator = $hydrator;
    }

    public function update($data)
    {
        $found{{ ResourceName }} = $this->entityManager->getRepository('{{ ModuleName }}\Entity\{{ ResourceName }}')->findById($data['id']);
        if(empty($found{{ ResourceName }})) {
            return 0;
        }
        unset($data['id']);
        $this->hydrator->hydrate($data, $found{{ ResourceName }});
        $this->entityManager->flush();

        return 1;
    }
}
