<?php

namespace {{ ModuleName }}\Service;

use Doctrine\ORM\EntityManager;
use Xsv\Doctrine\Hydrator\Hydrator;
use {{ ModuleName }}\Entity\{{ ResourceName }};

class Create{{ ResourceName }}DoctrineService
{
    /** @var EntityManager */
    protected $entityManager;

    /** @var Hydrator */
    protected $hydrator;

    /**
     * Create{{ ResourceName }}DoctrineService constructor.
     * @param EntityManager $entityManager
     * @param Hydrator $hydrator
     */
    public function __construct(EntityManager $entityManager, Hydrator $hydrator)
    {
        $this->entityManager = $entityManager;
        $this->hydrator = $hydrator;
    }

    public function create($data)
    {
        $new{{ ResourceName }} = new {{ ResourceName }}();

        $this->hydrator->hydrate($data, $new{{ ResourceName }});
        $this->entityManager->persist($new{{ ResourceName }});
        $this->entityManager->flush();

        return 1;
    }
}
